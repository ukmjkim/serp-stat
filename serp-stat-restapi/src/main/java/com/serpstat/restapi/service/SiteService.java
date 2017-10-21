package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteTags;

public interface SiteService {
	Site findById(long id);

	List<Site> findAllByUserId(long userId);

	List<Site> findAll();

	void saveSite(Site site);

	void updateSite(Site site);

	void deleteById(long id);

	boolean isSiteTitleUnique(Long id, Long userId, String title);
	
	SiteTags fetchTagsBySite(long id);
}
