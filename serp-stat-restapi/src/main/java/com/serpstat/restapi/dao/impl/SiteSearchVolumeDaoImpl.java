package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.SiteSearchVolumeDao;
import com.serpstat.restapi.model.SiteSearchVolume;

@Repository("siteSearchVolumeDao")
public class SiteSearchVolumeDaoImpl extends AbstractDao<Long, SiteSearchVolume> implements SiteSearchVolumeDao {
	static final Logger logger = LoggerFactory.getLogger(SiteSearchVolumeDaoImpl.class);

	public SiteSearchVolume findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteSearchVolume> findAllBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		List<SiteSearchVolume> entities = (List<SiteSearchVolume>) criteria.list();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<SiteSearchVolume> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.ge("crawlDate", fromDate));
		criteria.add(Restrictions.le("crawlDate", toDate));
		List<SiteSearchVolume> entities = (List<SiteSearchVolume>) criteria.list();
		return entities;
	}

	public SiteSearchVolume findBySiteIdAndCrawlDate(long siteId, int crawlDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.eq("crawlDate", crawlDate));
		SiteSearchVolume entity = (SiteSearchVolume) criteria.uniqueResult();
		return entity;
	}

	public void save(SiteSearchVolume entity) {
		persist(entity);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		SiteSearchVolume entity = (SiteSearchVolume) criteria.uniqueResult();
		delete(entity);
	}
}
