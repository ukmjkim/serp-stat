package com.serpstat.crawler.crawl;

import java.io.IOException;

import javax.net.ssl.SSLProtocolException;

import org.apache.http.HttpClientConnection;
import org.apache.http.config.Registry;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SniPoolingHttpClientConnectionManager extends PoolingHttpClientConnectionManager {
    public static final Logger logger =
            LoggerFactory.getLogger(SniPoolingHttpClientConnectionManager.class);

        public SniPoolingHttpClientConnectionManager(
            Registry<ConnectionSocketFactory> socketFactoryRegistry) {
            super(socketFactoryRegistry);
        }

        public SniPoolingHttpClientConnectionManager(
                Registry<ConnectionSocketFactory> socketFactoryRegistry, DnsResolver dnsResolver) {
            super(socketFactoryRegistry, dnsResolver);
        }

        @Override
        public void connect(final HttpClientConnection conn, final HttpRoute route,
                            final int connectTimeout, final HttpContext context) throws IOException {
            try {
                super.connect(conn, route, connectTimeout, context);
            } catch (SSLProtocolException e) {
                Boolean enableSniValue =
                    (Boolean) context.getAttribute(SniSSLConnectionSocketFactory.ENABLE_SNI);
                boolean enableSni = enableSniValue == null || enableSniValue;
                if (enableSni && e.getMessage() != null &&
                    e.getMessage().equals("handshake alert:  unrecognized_name")) {
                    logger.warn("Server saw wrong SNI host, retrying without SNI");
                    context.setAttribute(SniSSLConnectionSocketFactory.ENABLE_SNI, false);
                    super.connect(conn, route, connectTimeout, context);
                } else {
                    throw e;
                }
            }
        }
    }
