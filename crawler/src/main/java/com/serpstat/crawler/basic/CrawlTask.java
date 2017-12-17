package com.serpstat.crawler.basic;

import java.util.Date;

import com.serpstat.crawler.buffer.SearchResultBuffer;
import com.serpstat.crawler.crawl.SERPDataCapturer;
import com.serpstat.crawler.db.ConnectionPool;
import com.serpstat.crawler.model.Keyword;
import com.serpstat.crawler.model.KeywordSERP;

public class CrawlTask implements Runnable {
	private Keyword keyword;
	private SearchResultBuffer buffer;
	private ConnectionPool pool;

	public CrawlTask(Keyword keyword, SearchResultBuffer buffer) {
		this.pool = pool;
		this.keyword = keyword;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		System.out.println(keyword.getKeyword() + ": Running. " + new Date());
		SERPDataCapturer capturer = new SERPDataCapturer(keyword);
		KeywordSERP keywordSERP = capturer.load();
		buffer.add(keywordSERP);
	}

	public Keyword getKeyword() {
		return keyword;
	}

	public void setKeyword(Keyword keyword) {
		this.keyword = keyword;
	}
}
