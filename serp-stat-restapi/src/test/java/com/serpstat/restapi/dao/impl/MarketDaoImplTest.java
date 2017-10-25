package com.serpstat.restapi.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.MarketDao;
import com.serpstat.restapi.model.Market;

public class MarketDaoImplTest extends EntityPopulatedDataDaoImplTest {
	@Autowired
	MarketDao marketDao;

	@Test
	public void findById() {
		Assert.assertNotNull(marketDao.findById(1));
		Assert.assertNull(marketDao.findById(1000));
	}

	@Test
	public void findAll() {
		Assert.assertEquals(marketDao.findAll().size(), 1);
	}

	@Test
	public void save() {
		int currentCount = marketDao.findAll().size();

		Market market = new Market();
		market.setId(currentCount);
		market.setRegion("region");
		market.setLang("lang");
		marketDao.save(market);
		Assert.assertEquals(marketDao.findAll().size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = marketDao.findAll().size();
		marketDao.deleteById(1);
		Assert.assertEquals(marketDao.findAll().size(), currentCount - 1);
	}
}
