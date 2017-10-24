package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.SiteSearchVolume;

public interface SiteSearchVolumeService {
	SiteSearchVolume findById(long id);

	List<SiteSearchVolume> findAllBySiteId(long siteId);

	List<SiteSearchVolume> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate);

	SiteSearchVolume findBySiteIdAndCrawlDate(long siteId, int crawlDate);

	void saveSiteSearchVolume(SiteSearchVolume siteSearchVolume);

	void updateSiteSearchVolume(SiteSearchVolume siteSearchVolume);

	void deleteById(long id);

	boolean isSiteSearchVolumeUnique(Long id, Long siteId, Integer crawlDate);
}
