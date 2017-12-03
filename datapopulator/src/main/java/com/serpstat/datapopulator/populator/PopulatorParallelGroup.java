package com.serpstat.datapopulator.populator;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;
import com.serpstat.datapopulator.model.Keyword;

public class PopulatorParallelGroup implements Populator {
	private ConnectionPool pool;
	private List<Entity> dataSet;
	private String startDate;
	private String endDate;
	private ThreadPoolExecutor executor;
	private int numThreads;

	public PopulatorParallelGroup(ConnectionPool pool, List<Entity> dataSet, String startDate, String endDate) {
		this.pool = pool;
		this.dataSet = dataSet;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numThreads = Runtime.getRuntime().availableProcessors();
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
	}

	@Override
	public void populate() throws Exception {
		CountDownLatch endController = new CountDownLatch(numThreads);
		int length = dataSet.size() / numThreads;
		int startIndex = 0, endIndex = length;

		for (int i = 0; i < numThreads; i++) {
			System.out.printf("Processing from %d ~ %d\n", ((Keyword) dataSet.get(startIndex)).getId(),
					((Keyword) dataSet.get(endIndex)).getId());
			GroupTask task = new GroupTask(pool, startIndex, endIndex, dataSet, startDate, endDate,
					endController);
			startIndex = endIndex;
			if (i < numThreads - 2) {
				endIndex = endIndex + length;
			} else {
				endIndex = dataSet.size() - 1;
			}
			executor.execute(task);
		}
		endController.await();
	}

	@Override
	public void destroy() {
		executor.shutdown();
	}
}
