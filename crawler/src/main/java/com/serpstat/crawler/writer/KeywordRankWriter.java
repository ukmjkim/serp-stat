package com.serpstat.crawler.writer;

import com.serpstat.crawler.buffer.SearchResultBuffer;
import com.serpstat.crawler.db.ConnectionPool;
import com.serpstat.crawler.model.KeywordSERP;

public class KeywordRankWriter implements Runnable {
	private ConnectionPool pool;
	private SearchResultBuffer buffer;

	public KeywordRankWriter(ConnectionPool pool, SearchResultBuffer buffer) {
		this.pool = pool;
		this.buffer = buffer;
	}

	@Override
	public void run() {
		try {
			while (!Thread.currentThread().interrupted()) {
				KeywordSERP item = buffer.get();
				System.out.println(item);
			}
		} catch (InterruptedException e) {
			// Normal Exception
		}
	}
}
