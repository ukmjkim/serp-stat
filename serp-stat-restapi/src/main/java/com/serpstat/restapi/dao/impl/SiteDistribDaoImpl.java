package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.SiteDistribDao;
import com.serpstat.restapi.model.SiteDistrib;

@Repository("siteDistribDao")
public class SiteDistribDaoImpl extends AbstractDao<Long, SiteDistrib> implements SiteDistribDao {
	static final Logger logger = LoggerFactory.getLogger(SiteDistribDaoImpl.class);

	public SiteDistrib findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<SiteDistrib> findAllBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		List<SiteDistrib> entities = (List<SiteDistrib>) criteria.list();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<SiteDistrib> findAllBySiteIdInPeriod(long siteId, int fromDate, int toDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.ge("crawlDate", fromDate));
		criteria.add(Restrictions.le("crawlDate", toDate));
		List<SiteDistrib> entities = (List<SiteDistrib>) criteria.list();
		return entities;
	}

	public SiteDistrib findBySiteIdAndCrawlDate(long siteId, int crawlDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.eq("crawlDate", crawlDate));
		SiteDistrib entity = (SiteDistrib) criteria.uniqueResult();
		return entity;
	}

	public void save(SiteDistrib entity) {
		persist(entity);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		SiteDistrib entity = (SiteDistrib) criteria.uniqueResult();
		delete(entity);
	}
}
