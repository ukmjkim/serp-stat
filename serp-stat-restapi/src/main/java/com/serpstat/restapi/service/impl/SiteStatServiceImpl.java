package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.dao.SiteStatDao;
import com.serpstat.restapi.service.SiteStatService;

@Service("siteStatService")
@Transactional
public class SiteStatServiceImpl implements SiteStatService {
	@Autowired
	SiteStatDao dao;

	public SiteStat findById(long id) {
		return dao.findById(id);
	}

	public List<SiteStat> findAllBySiteId(long siteId) {
		return dao.findAllBySiteId(siteId);
	}

	public List<SiteStat> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate) {
		return dao.findAllBySiteIdInPeriod(siteId, fromDate, toDate);
	}

	public SiteStat findBySiteIdAndCrawlDate(long siteId, int crawlDate) {
		return dao.findBySiteIdAndCrawlDate(siteId, crawlDate);
	}

	public void saveSiteStat(SiteStat siteStat) {
		dao.save(siteStat);
	}

	public void updateSiteStat(SiteStat siteStat) {
		SiteStat entity = dao.findById(siteStat.getId());
		if (entity != null) {
			entity.setBackLinks(siteStat.getBackLinks());
			entity.setCrawlDate(siteStat.getCrawlDate());
			entity.setGoogleAverage(siteStat.getGoogleAverage());
			entity.setGoogleBaseAverage(siteStat.getGoogleBaseAverage());
			entity.setGoogleBaseRankingKeywords(siteStat.getGoogleBaseRankingKeywords());
			entity.setGoogleRankingKeywords(siteStat.getGoogleRankingKeywords());
			entity.setGoogleTopTenKeywords(siteStat.getGoogleTopTenKeywords());
			entity.setIndexed(siteStat.getIndexed());
			entity.setNonRankingValue(siteStat.getNonRankingValue());
			entity.setNonUniqueKeywords(siteStat.getNonUniqueKeywords());
			entity.setPageRank(siteStat.getPageRank());
			entity.setRankingKeywords(siteStat.getRankingKeywords());
			entity.setTotalKeywords(siteStat.getTotalKeywords());
			entity.setTrackedKeywords(siteStat.getTrackedKeywords());
			entity.setUniqueKeywords(siteStat.getUniqueKeywords());
			entity.setUntrackedKeywords(siteStat.getUntrackedKeywords());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isSiteStatUnique(Long id, Long siteId, Integer crawlDate) {
		SiteStat siteStat = dao.findBySiteIdAndCrawlDate(siteId, crawlDate);
		return (siteStat == null || ((id != null) && siteStat.getId() == id));
	}
}
