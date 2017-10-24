package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.TagSearchVolumeDao;
import com.serpstat.restapi.model.TagSearchVolume;

@Repository("tagSearchVolumeDao")
public class TagSearchVolumeDaoImpl extends AbstractDao<Long, TagSearchVolume> implements TagSearchVolumeDao {
	static final Logger logger = LoggerFactory.getLogger(TagSearchVolumeDaoImpl.class);

	public TagSearchVolume findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<TagSearchVolume> findAllByTagId(long tagId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		List<TagSearchVolume> entities = (List<TagSearchVolume>) criteria.list();
		return entities;
	}

	@SuppressWarnings("unchecked")
	public List<TagSearchVolume> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		criteria.add(Restrictions.ge("crawlDate", fromDate));
		criteria.add(Restrictions.le("crawlDate", toDate));
		List<TagSearchVolume> entities = (List<TagSearchVolume>) criteria.list();
		return entities;
	}

	public TagSearchVolume findByTagIdAndCrawlDate(long tagId, int crawlDate) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("tagId", tagId));
		criteria.add(Restrictions.eq("crawlDate", crawlDate));
		TagSearchVolume entity = (TagSearchVolume) criteria.uniqueResult();
		return entity;
	}

	public void save(TagSearchVolume entity) {
		persist(entity);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		TagSearchVolume entity = (TagSearchVolume) criteria.uniqueResult();
		delete(entity);
	}
}
