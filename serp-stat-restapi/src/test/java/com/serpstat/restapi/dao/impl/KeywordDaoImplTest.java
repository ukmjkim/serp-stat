package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.KeywordDao;
import com.serpstat.restapi.model.Device;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.model.Market;

public class KeywordDaoImplTest extends EntityPopulatedDataDaoImplTest {
	@Autowired
	KeywordDao keywordDao;

	@Test
	public void findById() {
		Assert.assertNotNull(keywordDao.findById(1));
		Assert.assertNull(keywordDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertEquals(keywordDao.findAllBySiteId(1L).size(), 1);
	}

	@Test
	public void findBySiteIdAndKeyword() {
		Keyword keyword = keywordDao.findBySiteIdAndKeyword(1L, "furniture livingroom sale");
		Assert.assertNotNull(keyword);
	}

	@Test
	public void save() {
		List<Keyword> keywords = keywordDao.findAllBySiteId(1L);
		int currentCount = keywords.size();

		long max = 1;
		for (Keyword keyword : keywords) {
			if (max < keyword.getId()) {
				max = keyword.getId();
			}
		}

		Device device = new Device();
		device.setId(1);
		device.setName("laptop");

		Market market = new Market();
		market.setId(1);
		market.setRegion("region");
		market.setLang("lang");
		market.setRegionName("regionName");
		market.setLangName("langName");

		Keyword keyword = new Keyword();
		keyword.setSiteId(1L);
		keyword.setKeyword("new keyword");
		keyword.setDevice(device);
		keyword.setMarket(market);
		keywordDao.save(keyword);
		Assert.assertTrue((keywordDao.findAllBySiteId(1L).size() > currentCount));
	}

	@Test
	public void deleteById() {
		int currentCount = keywordDao.findAllBySiteId(1L).size();
		keywordDao.deleteById(1);
		Assert.assertEquals(keywordDao.findAllBySiteId(1L).size(), currentCount - 1);
	}
}
