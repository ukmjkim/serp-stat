package com.serpstat.crawler.basic;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.serpstat.crawler.buffer.SearchResultBuffer;
import com.serpstat.crawler.db.ConnectionPool;
import com.serpstat.crawler.model.Keyword;
import com.serpstat.crawler.writer.KeywordRankWriter;

public class BasicCrawler implements Runnable {
	private ConnectionPool pool;
	private List<Keyword> keywordList;
	private ScheduledThreadPoolExecutor executor;
	private SearchResultBuffer buffer;
	private CountDownLatch latch = new CountDownLatch(1);

	public BasicCrawler(ConnectionPool pool, List<Keyword> keywordList) {
		this.pool = pool;
		this.keywordList = keywordList;
		executor = new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());
		buffer = new SearchResultBuffer();
	}

	@Override
	public void run() {
		KeywordRankWriter newsWriter = new KeywordRankWriter(pool, buffer);
		Thread t = new Thread(newsWriter);
		t.start();

		for (Keyword keyword : keywordList) {
			CrawlTask task = new CrawlTask(keyword, buffer);
			System.out.println("Task: " + task.getKeyword().getKeyword());
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			executor.scheduleWithFixedDelay(task, 0, 1, TimeUnit.MINUTES);
		}

		synchronized (this) {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		System.out.println("Shutting down the executor.");
		executor.shutdown();
		t.interrupt();
		System.out.println("The system has finished.");
	}

	public void shutdown() {
		latch.countDown();
	}
}
