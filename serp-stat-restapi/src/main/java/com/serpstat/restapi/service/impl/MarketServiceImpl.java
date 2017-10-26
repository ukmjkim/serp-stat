package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.MarketDao;
import com.serpstat.restapi.model.Market;

@Service("marketService")
@Transactional
public class MarketServiceImpl {
	@Autowired
	MarketDao dao;

	public Market findById(int id) {
		return dao.findById(id);
	}

	public List<Market> findAll() {
		return dao.findAll();
	}

	public void saveMarket(Market market) {
		dao.save(market);
	}

	public void updateMarket(Market market) {
		Market entity = dao.findById(market.getId());
		if (entity != null) {
			entity.setRegion(market.getRegion());
			entity.setLang(market.getLang());
			entity.setRegionName(market.getRegionName());
			entity.setLangName(market.getLangName());
		}
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}

	public boolean isMarketUnique(String region, String lang) {
		Market entity = dao.findByRegionAneLang(region, lang);
		return (entity == null
				|| ((region != null && lang != null) && (entity.getRegion() == region && entity.getLang() == lang)));
	}
}
