package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.SiteStat;

public interface SiteStatService {
	SiteStat findById(long id);

	List<SiteStat> findAllBySiteId(long siteId);

	List<SiteStat> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate);

	SiteStat findBySiteIdAndCrawlDate(long siteId, int crawlDate);

	void saveSiteStat(SiteStat siteStat);

	void updateSiteStat(SiteStat siteStat);

	void deleteById(long id);

	boolean isSiteStatUnique(Long id, Long siteId, Integer crawlDate);
}
