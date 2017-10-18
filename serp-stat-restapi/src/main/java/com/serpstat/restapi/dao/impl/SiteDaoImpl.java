package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.SiteDao;
import com.serpstat.restapi.model.Site;

@Repository("siteDao")
public class SiteDaoImpl extends AbstractDao<Long, Site> implements SiteDao {
	static final Logger logger = LoggerFactory.getLogger(SiteDaoImpl.class);

	public Site findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Site> findAllByUserId(long userId) {
		Criteria criteria = createEntityCriteria();
		Criteria userCriteria = criteria.createCriteria("user");
		userCriteria.add(Restrictions.eq("id", userId));
		return (List<Site>) criteria.list();
	}

	public Site findByUserIdAndTitle(long userId, String title) {
		Criteria criteria = createEntityCriteria();
		Criteria userCriteria = criteria.createCriteria("user");
		userCriteria.add(Restrictions.eq("id", userId));
		criteria.add(Restrictions.eq("title", title));
		return (Site) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Site> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("title"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Site>) criteria.list();

	}

	public void save(Site site) {
		persist(site);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		Site site = (Site) criteria.uniqueResult();
		delete(site);
	}
}
