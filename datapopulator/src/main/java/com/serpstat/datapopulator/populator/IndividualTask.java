package com.serpstat.datapopulator.populator;

import java.util.concurrent.CountDownLatch;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.populator.table.KeywordDailyRankPopulator;
import com.serpstat.datapopulator.populator.table.KeywordRankPopulator;
import com.serpstat.datapopulator.populator.table.KeywordStatPopulator;

public class IndividualTask implements Runnable {
	private ConnectionPool pool;
	private Long keywordId;
	private String startDate;
	private String endDate;
	private CountDownLatch endController;

	public IndividualTask(ConnectionPool pool, Long keywordId, String startDate, String endDate,
			CountDownLatch endController) {
		this.pool = pool;
		this.keywordId = keywordId;
		this.startDate = startDate;
		this.endDate = endDate;
		this.endController = endController;
	}

	@Override
	public void run() {
		try {
			KeywordDailyRankPopulator.populate(pool, keywordId, startDate, endDate);
			KeywordRankPopulator.populate(pool, keywordId, startDate, endDate);
			KeywordStatPopulator.populate(pool, keywordId, startDate, endDate);

			System.out.printf("Keyword(id=%d) tables are populated\n", keywordId);

			endController.countDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
