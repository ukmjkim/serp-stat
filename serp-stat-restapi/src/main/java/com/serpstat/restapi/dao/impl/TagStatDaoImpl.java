package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.TagStatDao;
import com.serpstat.restapi.model.TagStat;

@Repository("tagStatDao")
public class TagStatDaoImpl extends AbstractDao<Long, TagStat> implements TagStatDao {
	static final Logger logger = LoggerFactory.getLogger(TagStatDaoImpl.class);

	public TagStat findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<TagStat> findAllByTagId(long tagId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		List<TagStat> entities = (List<TagStat>) criteria.list();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<TagStat> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		criteria.add(Restrictions.ge("crawlDate", fromDate));
		criteria.add(Restrictions.le("crawlDate", toDate));
		List<TagStat> entities = (List<TagStat>) criteria.list();
		return entities;
	}

	public TagStat findByTagIdAndCrawlDate(long tagId, int crawlDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		criteria.add(Restrictions.eq("crawlDate", crawlDate));
		TagStat entity = (TagStat) criteria.uniqueResult();
		return entity;
	}

	public void save(TagStat entity) {
		persist(entity);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		TagStat entity = (TagStat) criteria.uniqueResult();
		delete(entity);
	}
}
