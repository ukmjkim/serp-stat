package com.serpstat.restapi.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.SiteDao;
import com.serpstat.restapi.dao.TagDao;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteTags;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.service.SiteService;

@Service("siteService")
@Transactional
public class SiteServiceImpl implements SiteService {
	static final Logger logger = LoggerFactory.getLogger(SiteServiceImpl.class);

	@Autowired
	SiteDao dao;

	@Autowired
	TagDao tagDao;

	public Site findById(long id) {
		return dao.findById(id);
	}

	public List<Site> findAllByUserId(long userId) {
		return dao.findAllByUserId(userId);
	}

	public List<Site> findAll() {
		return dao.findAll();
	}

	public void saveSite(Site site) {
		dao.save(site);
	}

	public void updateSite(Site site) {
		Site entity = dao.findById(site.getId());
		if (entity != null) {
			entity.setTitle(site.getTitle());
			entity.setUrl(site.getUrl());
			entity.setTracking(site.getTracking());
			entity.setDropWWWPrefix(site.getDropWWWPrefix());
			entity.setDropDirectories(site.getDropDirectories());
			entity.setContactEmail(site.getContactEmail());
			entity.setTreatNonRankingAs(site.getTreatNonRankingAs());
			entity.setNonRankingValue(site.getNonRankingValue());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isSiteTitleUnique(Long id, Long userId, String title) {
		Site site = dao.findByUserIdAndTitle(userId, title);
		return (site == null || ((id != null) && site.getId() == id));
	}

	public SiteTags fetchTagsBySite(long id) {
		SiteTags siteTags = new SiteTags();
		Site site = dao.findById(id);
		if (site == null) {
			return siteTags;
		}
		siteTags.setId(site.getId());;
		siteTags.setTitle(site.getTitle());
		siteTags.setUrl(site.getUrl());		
		List<Tag> tags = tagDao.findAllBySiteId(id);
		siteTags.setTags(tags);

		return siteTags;
	}
}
