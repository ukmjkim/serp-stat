package com.serpstat.datapopulator.populator;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;
import com.serpstat.datapopulator.model.Keyword;

public class PopulatorParallelIndividual implements Populator {
	private ConnectionPool pool;
	private List<Entity> dataSet;
	private String startDate;
	private String endDate;
	private ThreadPoolExecutor executor;
	private int numThreads;

	public PopulatorParallelIndividual(ConnectionPool pool, List<Entity> dataSet, String startDate, String endDate) {
		this.pool = pool;
		this.dataSet = dataSet;
		this.startDate = startDate;
		this.endDate = endDate;
		this.numThreads = Runtime.getRuntime().availableProcessors();
		executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
	}

	@Override
	public void populate() throws Exception {
		CountDownLatch endController = new CountDownLatch(dataSet.size());
		for (Entity entity : dataSet) {
			System.out.println(entity);
			IndividualTask task = new IndividualTask(pool, ((Keyword) entity).getId(), startDate, endDate,
					endController);
			executor.execute(task);
		}
		endController.await();
	}

	@Override
	public void destroy() {
		executor.shutdown();
	}
}
