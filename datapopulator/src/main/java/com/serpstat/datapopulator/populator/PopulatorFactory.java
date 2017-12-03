package com.serpstat.datapopulator.populator;

import java.util.List;

import com.serpstat.datapopulator.db.ConnectionPool;
import com.serpstat.datapopulator.model.Entity;

public class PopulatorFactory {
	public static Populator getPopulator(ConnectionPool pool, List<Entity> dataSet, String startDate, String endDate,
			boolean parallel, boolean group) {
		if (parallel) {
			if (group) {
				return new PopulatorParallelGroup(pool, dataSet, startDate, endDate);
			} else {
				return new PopulatorParallelIndividual(pool, dataSet, startDate, endDate);
			}
		}
		return new SerialPopulator(pool, dataSet, startDate, endDate);
	}
}
