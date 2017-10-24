package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.TagDistribDao;
import com.serpstat.restapi.model.TagDistrib;

@Repository("tagDistribDao")
public class TagDistribDaoImpl extends AbstractDao<Long, TagDistrib> implements TagDistribDao {
	static final Logger logger = LoggerFactory.getLogger(TagDistribDaoImpl.class);

	public TagDistrib findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<TagDistrib> findAllByTagId(long tagId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		List<TagDistrib> entities = (List<TagDistrib>) criteria.list();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<TagDistrib> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		criteria.add(Restrictions.ge("crawlDate", fromDate));
		criteria.add(Restrictions.le("crawlDate", toDate));
		List<TagDistrib> entities = (List<TagDistrib>) criteria.list();
		return entities;
	}

	public TagDistrib findByTagIdAndCrawlDate(long tagId, int crawlDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		criteria.add(Restrictions.eq("crawlDate", crawlDate));
		TagDistrib entity = (TagDistrib) criteria.uniqueResult();
		return entity;
	}

	public void save(TagDistrib entity) {
		persist(entity);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		TagDistrib entity = (TagDistrib) criteria.uniqueResult();
		delete(entity);
	}

}
