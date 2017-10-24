package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.SiteSearchVolume;

public interface SiteSearchVolumeDao {
	SiteSearchVolume findById(long id);

	List<SiteSearchVolume> findAllBySiteId(long siteId);

	List<SiteSearchVolume> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate);

	SiteSearchVolume findBySiteIdAndCrawlDate(long siteId, int crawlDate);

	void save(SiteSearchVolume siteStat);

	void deleteById(long id);
}
