package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.SiteStatDao;
import com.serpstat.restapi.model.SiteStat;

@Repository("siteStatDao")
public class SiteStatDaoImpl extends AbstractDao<Long, SiteStat> implements SiteStatDao {
	static final Logger logger = LoggerFactory.getLogger(SiteStatDaoImpl.class);

	public SiteStat findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteStat> findAllBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		List<SiteStat> entities = (List<SiteStat>) criteria.list();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<SiteStat> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.ge("crawlDate", fromDate));
		criteria.add(Restrictions.le("crawlDate", toDate));
		List<SiteStat> entities = (List<SiteStat>) criteria.list();
		return entities;
	}

	public SiteStat findBySiteIdAndCrawlDate(long siteId, int crawlDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.eq("crawlDate", crawlDate));
		SiteStat entity = (SiteStat) criteria.uniqueResult();
		return entity;
	}

	public void save(SiteStat entity) {
		persist(entity);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		SiteStat entity = (SiteStat) criteria.uniqueResult();
		delete(entity);
	}
}
