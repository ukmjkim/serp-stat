package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.SiteSearchVolumeDao;
import com.serpstat.restapi.model.SiteSearchVolume;
import com.serpstat.restapi.service.SiteSearchVolumeService;

@Service("siteSearchVolumeService")
@Transactional
public class SiteSearchVolumeServiceImpl implements SiteSearchVolumeService {
	@Autowired
	SiteSearchVolumeDao dao;

	public SiteSearchVolume findById(long id) {
		return dao.findById(id);
	}

	public List<SiteSearchVolume> findAllBySiteId(long siteId) {
		return dao.findAllBySiteId(siteId);
	}

	public List<SiteSearchVolume> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate) {
		return dao.findAllBySiteIdInPeriod(siteId, fromDate, toDate);
	}

	public SiteSearchVolume findBySiteIdAndCrawlDate(long siteId, int crawlDate) {
		return dao.findBySiteIdAndCrawlDate(siteId, crawlDate);
	}

	public void saveSiteSearchVolume(SiteSearchVolume siteSearchVolume) {
		dao.save(siteSearchVolume);
	}

	public void updateSiteSearchVolume(SiteSearchVolume siteSearchVolume) {
		SiteSearchVolume entity = dao.findById(siteSearchVolume.getId());
		if (entity != null) {
			entity.setGlobalAggregateSV(siteSearchVolume.getGlobalAggregateSV());
			entity.setRegionalAverageSV(siteSearchVolume.getGlobalAverageSV());
			entity.setRegionalAggregateSV(siteSearchVolume.getRegionalAggregateSV());
			entity.setRegionalAverageSV(siteSearchVolume.getRegionalAverageSV());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isSiteSearchVolumeUnique(Long id, Long siteId, Integer crawlDate) {
		SiteSearchVolume siteSearchVolume = dao.findBySiteIdAndCrawlDate(siteId, crawlDate);
		return (siteSearchVolume == null || ((id != null) && siteSearchVolume.getId() == id));
	}
}
