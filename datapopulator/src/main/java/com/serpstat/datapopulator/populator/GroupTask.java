package com.serpstat.datapopulator.populator;

import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;
import com.serpstat.datapopulator.model.Keyword;
import com.serpstat.datapopulator.populator.table.KeywordDailyRankPopulator;
import com.serpstat.datapopulator.populator.table.KeywordRankPopulator;
import com.serpstat.datapopulator.populator.table.KeywordStatPopulator;

public class GroupTask implements Runnable {
	private ConnectionPool pool;
	private int startIndex;
	private int endIndex;
	private List<Entity> dataSet;
	private String startDate;
	private String endDate;
	private CountDownLatch endController;

	public GroupTask(ConnectionPool pool, int startIndex, int endIndex, List<Entity> dataSet, String startDate,
			String endDate, CountDownLatch endController) {
		this.pool = pool;
		this.startIndex = startIndex;
		this.endIndex = endIndex;
		this.dataSet = dataSet;
		this.startDate = startDate;
		this.endDate = endDate;
		this.endController = endController;
	}

	@Override
	public void run() {
		try {
			for (int index = startIndex; index < endIndex; index++) {
				Entity data = dataSet.get(index);
				long keywordId = ((Keyword) data).getId();
				KeywordDailyRankPopulator.populate(pool, keywordId, startDate, endDate);
				KeywordRankPopulator.populate(pool, keywordId, startDate, endDate);
				KeywordStatPopulator.populate(pool, keywordId, startDate, endDate);

				System.out.printf("Keyword(id=%d) tables are populated\n", keywordId);
			}
			endController.countDown();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
