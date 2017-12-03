package com.serpstat.datapopulator.populator;

import java.util.List;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;
import com.serpstat.datapopulator.model.Keyword;
import com.serpstat.datapopulator.populator.table.KeywordDailyRankPopulator;
import com.serpstat.datapopulator.populator.table.KeywordRankPopulator;
import com.serpstat.datapopulator.populator.table.KeywordStatPopulator;

public class SerialPopulator implements Populator {
	private ConnectionPool pool;
	private List<Entity> dataSet;
	private String startDate;
	private String endDate;

	public SerialPopulator(ConnectionPool pool, List<Entity> dataSet, String startDate, String endDate) {
		this.pool = pool;
		this.dataSet = dataSet;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	@Override
	public void populate() throws Exception {
		for (Entity entity : dataSet) {
			System.out.println(entity);

			KeywordDailyRankPopulator.populate(pool, ((Keyword) entity).getId(), startDate, endDate);
			KeywordRankPopulator.populate(pool, ((Keyword) entity).getId(), startDate, endDate);
			KeywordStatPopulator.populate(pool, ((Keyword) entity).getId(), startDate, endDate);
		}
	}

	@Override
	public void destroy() {

	}
}
