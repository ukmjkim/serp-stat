package com.serpstat.crawler.crawl;

import static com.serpstat.crawler.common.Constants.OUTPUT_FOLDER;
import static com.serpstat.crawler.common.Constants.SERP_URI;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.serpstat.crawler.config.CrawlConfig;
import com.serpstat.crawler.model.Keyword;
import com.serpstat.crawler.model.KeywordSERP;

public class SERPDataCapturer {
	private Keyword keyword;

	public SERPDataCapturer(Keyword keyword) {
		this.keyword = keyword;
	}

	public KeywordSERP load() {
		KeywordSERP keywordSERP = fetchPage();
		return keywordSERP;
	}

	private KeywordSERP fetchPage() {
		String url = "";
		try {
			url = SERP_URI + "&num=100&q=" + URLEncoder.encode(keyword.getKeyword(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		KeywordSERP keywordSERP = new KeywordSERP();
		keywordSERP.setId(keyword.getId());
		keywordSERP.setUrl(keyword.getUrl());
		keywordSERP.setSiteId(keyword.getSiteId());
		keywordSERP.setKeyword(keyword.getKeyword());

		Map<String, String> serpRank = new HashMap<>();

		String fileName = "output/" + keyword.getId() + ".html";
        final File f = new File(fileName);
        Response response = null;
		Document doc = null;
        if (f.exists()) {
        	try {
				doc = Jsoup.parse(f, "UTF-8", "");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        } else {
			try {
				response = Jsoup.connect(url).userAgent("Cert client").timeout(5000).execute();
		        FileUtils.writeStringToFile(f, response.body(), "UTF-8");
				doc = response.parse();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

        if (doc != null) {
			try {
				Elements divs = doc.select("div");
				int rank = 1;
				for (Element div : divs) {
					String attr = div.attr("class");
					if ("g".equals(attr)) {
						Elements h3List = div.select("h3");
						for (Element h3 : h3List) {
							Elements links = h3.select("a[href]");
							for (Element link : links) {
								String domain = getDomainName(link.attr("href"));
								if (domain.length() > 0) {
									String key = String.valueOf(rank);
									serpRank.put(key, domain);
									rank++;
								}
							}
						}
					}
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }

		keywordSERP.setSerpResult(serpRank);
		return keywordSERP;
	}

	private KeywordSERP fetchFakeData() {
		String url = "";
		try {
			url = SERP_URI + "&num=100&q=" + URLEncoder.encode(keyword.getKeyword(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		KeywordSERP keywordSERP = new KeywordSERP();
		keywordSERP.setId(keyword.getId());
		keywordSERP.setUrl(keyword.getUrl());
		keywordSERP.setSiteId(keyword.getSiteId());
		keywordSERP.setKeyword(keyword.getKeyword());

		Map<String, String> serpRank = new HashMap<>();

		String[] domains = { "www.wayfair.ca", "roomdecofurniture.ca", "www.anthropologie.com", "www.jcperreault.com",
				"www.structube.com", "www.bouclair.com", "www.decorium.com", "www.kirklands.com", "www.westelm.ca",
				"www.westelm.com", "www.overstock.com", "f2furnishings.ca", "www.tkmaxx.com", "decorezafurniture.com",
				"www.cocorepublic.com.au", "www.cocorepublic.com.au", "www.marshbeckreproductionfurniture.co.uk",
				"www.wayfair.co.uk", "www.pinterest.ca", "www.warrenevans.com", "books.google.ca", "www.next.co.uk",
				"cbcgate.com", "www.kijiji.ca", "stjacobsfurnishings.ca", "www.amazon.com", "www.isffuarcilik.com",
				"www.livingspaces.com", "www.gallerydirect.co.uk", "jaimeguevara.co", "www.ebay.co.uk",
				"xinaris.com.cy", "www.bedbathandbeyond.com", "www.touchofclass.com", "www.livinghomes.co.uk",
				"www.bedbathandbeyond.ca", "www.miniland.ca", "www.hatfieldsofcolchester.com", "www.linenchest.com",
				"www.telegraph.co.uk" };
		List<String> domainList = Arrays.asList(domains);
		Collections.shuffle(domainList);

		for (int i = 0; i < domainList.size(); i++) {
			serpRank.put(String.valueOf(i + 1), domainList.get(i));
			keywordSERP.setSerpResult(serpRank);
		}
		return keywordSERP;
	}

	private String getDomainName(String url) {
		Matcher matcher;
		String DOMAIN_NAME_PATTERN = "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,15}";
		Pattern patrn = Pattern.compile(DOMAIN_NAME_PATTERN);

		String domainName = "";
		matcher = patrn.matcher(url);

		if (matcher.find()) {
			domainName = matcher.group(0).toLowerCase().trim();
		}

		return domainName;
	}

	private void fetchSSLPage(String keyword) throws Exception {
		String search_uri = SERP_URI + "&q=" + URLEncoder.encode(keyword, "UTF-8");

		String crawlStorageFolder = OUTPUT_FOLDER;
		CrawlConfig config = new CrawlConfig();
		config.setCrawlStorageFolder(crawlStorageFolder);
		config.setPolitenessDelay(1000);
		config.setMaxDepthOfCrawling(1);
		config.setMaxPagesToFetch(1000);
		config.setResumableCrawling(false);
		WebURL webUrl = new WebURL();
		webUrl.setURL(search_uri);

		PageFetcher pageFetcher = new PageFetcher(config);
		PageFetchResult fetchResult = pageFetcher.fetchPage(webUrl);

		System.out.println("response size: " + fetchResult.getEntity().getContentLength());
		BufferedReader rd = new BufferedReader(new InputStreamReader(fetchResult.getEntity().getContent()));
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}
		System.out.println(result.toString());
		Document document = Jsoup.parse(result.toString());
		Element divTag = document.getElementById("g");
		System.out.println(divTag.text());
	}
}
