package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.Market;

public interface MarketDao {
	Market findById(int id);

	List<Market> findAll();

	void save(Market market);

	void deleteById(int id);
}