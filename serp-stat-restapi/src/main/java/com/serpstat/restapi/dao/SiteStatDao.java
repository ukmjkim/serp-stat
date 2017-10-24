package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.SiteStat;

public interface SiteStatDao {
	SiteStat findById(long id);

	List<SiteStat> findAllBySiteId(long siteId);

	List<SiteStat> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate);

	SiteStat findBySiteIdAndCrawlDate(long siteId, int crawlDate);

	void save(SiteStat siteStat);

	void deleteById(long id);
}
