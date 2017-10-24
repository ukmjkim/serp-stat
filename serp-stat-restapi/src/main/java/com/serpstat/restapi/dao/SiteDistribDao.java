package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.SiteDistrib;

public interface SiteDistribDao {
	SiteDistrib findById(long id);

	List<SiteDistrib> findAllBySiteId(long siteId);

	List<SiteDistrib> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate);

	SiteDistrib findBySiteIdAndCrawlDate(long siteId, int crawlDate);

	void save(SiteDistrib siteDistrib);

	void deleteById(long id);
}
