package com.serpstat.crawler.advanced;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.serpstat.crawler.db.ConnectionPool;
import com.serpstat.crawler.model.Keyword;

public class AdvancedCrawler implements Runnable {
	private ConnectionPool pool;
	private List<Keyword> dataSet;

	private CountDownLatch latch = new CountDownLatch(1);

	public AdvancedCrawler(ConnectionPool pool, List<Keyword> dataSet) {
		this.pool = pool;
		this.dataSet = dataSet;
	}

	@Override
	public void run() {

	}

	public void shutdown() {
		latch.countDown();
	}
}
