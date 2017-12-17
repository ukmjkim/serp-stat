package com.serpstat.crawler.crawl;

import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.Date;

import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContexts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.serpstat.crawler.config.CrawlConfig;
import com.serpstat.crawler.exception.PageBiggerThanMaxSizeException;

import edu.uci.ics.crawler4j.url.URLCanonicalizer;

public class PageFetcher {
	protected static final Logger logger = LoggerFactory.getLogger(PageFetcher.class);
	protected final Object mutex = new Object();
	protected PoolingHttpClientConnectionManager connectionManager;
	protected CloseableHttpClient httpClient;
	protected long lastFetchTime = 0;
	protected IdleConnectionMonitorThread connectionMonitorThread = null;
	protected CrawlConfig config;

	public PageFetcher(CrawlConfig config) {
		this.config = config;

		RequestConfig requestConfig = RequestConfig.custom()
				.setExpectContinueEnabled(false)
				.setCookieSpec(config.getCookiePolicy())
				.setRedirectsEnabled(false)
				.setSocketTimeout(config.getSocketTimeout())
				.setConnectTimeout(config.getConnectionTimeout())
				.build();
		RegistryBuilder<ConnectionSocketFactory> connRegistryBuilder = RegistryBuilder.create();
		connRegistryBuilder.register("http", PlainConnectionSocketFactory.INSTANCE);
		if (config.isIncludeHttpsPages()) {
			try {
				SSLContext sslContext = 
					SSLContexts.custom().loadTrustMaterial(null, new TrustStrategy() {
						@Override
						public boolean isTrusted(final X509Certificate[] chain, String authType) {
							return true;
						}
					}).build();
				SSLConnectionSocketFactory sslsf = new SniSSLConnectionSocketFactory(sslContext,
						NoopHostnameVerifier.INSTANCE);
				connRegistryBuilder.register("https", sslsf);
			} catch (Exception e) {
				System.out.println("Exception thrown while trying to register https");
				e.printStackTrace();
			}
		}

		Registry<ConnectionSocketFactory> connRegistry = connRegistryBuilder.build();
		connectionManager = new SniPoolingHttpClientConnectionManager(connRegistry, config.getDnsResolver());
		connectionManager.setMaxTotal(config.getMaxTotalConnections());
		connectionManager.setDefaultMaxPerRoute(config.getMaxConnectionsPerHost());

		HttpClientBuilder clientBuilder = HttpClientBuilder.create();
		clientBuilder.setDefaultRequestConfig(requestConfig);
		clientBuilder.setConnectionManager(connectionManager);
		clientBuilder.setUserAgent(config.getUserAgentString());
		clientBuilder.setDefaultHeaders(config.getDefaultHeaders());

		if (config.getProxyHost() != null) {
			if (config.getProxyUsername() != null) {
				BasicCredentialsProvider credentialsProvider = new BasicCredentialsProvider();
				credentialsProvider.setCredentials(
						new AuthScope(config.getProxyHost(), config.getProxyPort()),
						new UsernamePasswordCredentials(config.getProxyUsername(),
														config.getProxyPassword()));
				clientBuilder.setDefaultCredentialsProvider(credentialsProvider);
			}

			HttpHost proxy = new HttpHost(config.getProxyHost(), config.getProxyPort());
			clientBuilder.setProxy(proxy);
			logger.debug("Working through Proxy: {}", proxy.getHostName());
		}

		httpClient = clientBuilder.build();
		if (connectionMonitorThread == null) {
			connectionMonitorThread = new IdleConnectionMonitorThread(connectionManager);
		}
		connectionMonitorThread.start();
	}

	public PageFetchResult fetchPage(WebURL webUrl)
			throws InterruptedException, IOException, PageBiggerThanMaxSizeException {
		// Getting URL, setting headers & content
		
		System.out.println("fetchPage: " + webUrl.getURL());

		PageFetchResult fetchResult = new PageFetchResult();
		String toFetchURL = webUrl.getURL();
		HttpUriRequest request = null;
		try {
			request = newHttpUriRequest(toFetchURL);
			// Applying Politeness delay
			synchronized (mutex) {
				long now = (new Date()).getTime();
				if ((now - lastFetchTime) < config.getPolitenessDelay()) {
					Thread.sleep(config.getPolitenessDelay() - (now - lastFetchTime));
				}
				lastFetchTime = (new Date()).getTime();
			}

			CloseableHttpResponse response = httpClient.execute(request);

			fetchResult.setEntity(response.getEntity());
			fetchResult.setResponseHeaders(response.getAllHeaders());

			// Setting HttpStatus
			int statusCode = response.getStatusLine().getStatusCode();

			// If Redirect ( 3xx )
			if (statusCode == HttpStatus.SC_MOVED_PERMANENTLY || statusCode == HttpStatus.SC_MOVED_TEMPORARILY
					|| statusCode == HttpStatus.SC_MULTIPLE_CHOICES || statusCode == HttpStatus.SC_SEE_OTHER
					|| statusCode == HttpStatus.SC_TEMPORARY_REDIRECT || statusCode == 308) { // todo
																								// follow
				// https://issues.apache.org/jira/browse/HTTPCORE-389

				Header header = response.getFirstHeader("Location");
				if (header != null) {
					String movedToUrl = URLCanonicalizer.getCanonicalURL(header.getValue(), toFetchURL);
					fetchResult.setMovedToUrl(movedToUrl);
				}
			} else if (statusCode >= 200 && statusCode <= 299) { // is 2XX,
																	// everything
																	// looks ok
				fetchResult.setFetchedUrl(toFetchURL);
				String uri = request.getURI().toString();
				if (!uri.equals(toFetchURL)) {
					if (!URLCanonicalizer.getCanonicalURL(uri).equals(toFetchURL)) {
						fetchResult.setFetchedUrl(uri);
					}
				}

				// Checking maximum size
				if (fetchResult.getEntity() != null) {

					long size = fetchResult.getEntity().getContentLength();
					if (size == -1) {
						Header length = response.getLastHeader("Content-Length");
						if (length == null) {
							length = response.getLastHeader("Content-length");
						}
						if (length != null) {
							size = Integer.parseInt(length.getValue());
						}
					}
					if (size > config.getMaxDownloadSize()) {
						// fix issue #52 - consume entity
						response.close();
						throw new PageBiggerThanMaxSizeException(size);
					}
				}
			}

			fetchResult.setStatusCode(statusCode);
			return fetchResult;

		} finally { // occurs also with thrown exceptions
			if ((fetchResult.getEntity() == null) && (request != null)) {
				request.abort();
			}
		}
	}

	public synchronized void shutDown() {
		if (connectionMonitorThread != null) {
			connectionManager.shutdown();
			connectionMonitorThread.shutdown();
		}
	}

	protected HttpUriRequest newHttpUriRequest(String url) {
		return new HttpGet(url);
	}

}
