package com.serpstat.restapi.dao.impl;

import java.util.List;

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
	public void findByRegionAneLang() {
		Assert.assertEquals(marketDao.findByRegionAneLang("US", "en").getRegion(), "US");
	}

	@Test
	public void save() {
		List<Market> markets = marketDao.findAll();
		int currentCount = markets.size();

		int max = 1;
		for (Market market : markets) {
			if (max < market.getId()) {
				max = market.getId();
			}
		}

		Market market = new Market();
		market.setId(max + 1);
		market.setRegion("region");
		market.setLang("lang");
		market.setRegionName("regionName");
		market.setLangName("langName");
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
