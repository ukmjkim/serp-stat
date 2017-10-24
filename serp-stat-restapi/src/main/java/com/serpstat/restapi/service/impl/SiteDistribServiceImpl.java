package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.SiteDistribDao;
import com.serpstat.restapi.model.SiteDistrib;
import com.serpstat.restapi.service.SiteDistribService;

@Service("siteDistribService")
@Transactional
public class SiteDistribServiceImpl implements SiteDistribService {
	@Autowired
	SiteDistribDao dao;

	public SiteDistrib findById(long id) {
		return dao.findById(id);
	}

	public List<SiteDistrib> findAllBySiteId(long siteId) {
		return dao.findAllBySiteId(siteId);
	}

	public List<SiteDistrib> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate) {
		return dao.findAllBySiteIdInPeriod(siteId, fromDate, toDate);
	}

	public SiteDistrib findBySiteIdAndCrawlDate(long siteId, int crawlDate) {
		return dao.findBySiteIdAndCrawlDate(siteId, crawlDate);
	}

	public void saveSiteDistrib(SiteDistrib siteDistrib) {
		dao.save(siteDistrib);
	}

	public void updateSiteDistrib(SiteDistrib siteDistrib) {
		SiteDistrib entity = dao.findById(siteDistrib.getId());
		if (entity != null) {
			entity.setOne(siteDistrib.getOne());
			entity.setTwo(siteDistrib.getTwo());
			entity.setThree(siteDistrib.getThree());
			entity.setFour(siteDistrib.getFour());
			entity.setFive(siteDistrib.getFive());
			entity.setSixToTen(siteDistrib.getSixToTen());
			entity.setElevenToTwenty(siteDistrib.getElevenToTwenty());
			entity.setTwentyOneToThirty(siteDistrib.getTwentyOneToThirty());
			entity.setThirtyOneToForty(siteDistrib.getThirtyOneToForty());
			entity.setFortyOneToFifty(siteDistrib.getFortyOneToFifty());
			entity.setFiftyOneToHundred(siteDistrib.getFiftyOneToHundred());
			entity.setNonRanking(siteDistrib.getNonRanking());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isSiteDistribUnique(Long id, Long siteId, Integer crawlDate) {
		SiteDistrib siteDistrib = dao.findBySiteIdAndCrawlDate(siteId, crawlDate);
		return (siteDistrib == null || ((id != null) && siteDistrib.getId() == id));
	}
}
