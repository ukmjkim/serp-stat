package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.SiteDistrib;

public interface SiteDistribService {
	SiteDistrib findById(long id);

	List<SiteDistrib> findAllBySiteId(long siteId);

	List<SiteDistrib> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate);

	SiteDistrib findBySiteIdAndCrawlDate(long siteId, int crawlDate);

	void saveSiteDistrib(SiteDistrib siteDistrib);

	void updateSiteDistrib(SiteDistrib siteDistrib);

	void deleteById(long id);

	boolean isSiteDistribUnique(Long id, Long siteId, Integer crawlDate);
}
