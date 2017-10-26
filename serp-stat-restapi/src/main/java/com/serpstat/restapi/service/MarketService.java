package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.Market;

public interface MarketService {
	Market findById(int id);

	List<Market> findAll();

	void saveMarket(Market market);

	void updateMarket(Market market);

	void deleteById(int id);

	boolean isMarketUnique(String region, String lang);
}
