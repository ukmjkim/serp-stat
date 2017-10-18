package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.Site;

public interface SiteDao {
	Site findById(long id);

	List<Site> findAllByUserId(long userId);

	Site findByUserIdAndTitle(long userId, String title);

	List<Site> findAll();

	void save(Site site);

	void deleteById(long id);
}
