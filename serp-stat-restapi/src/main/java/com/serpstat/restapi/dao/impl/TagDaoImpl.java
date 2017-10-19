package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.TagDao;
import com.serpstat.restapi.model.Tag;

@Repository("tagDao")
public class TagDaoImpl extends AbstractDao<Long, Tag> implements TagDao {
	static final Logger logger = LoggerFactory.getLogger(TagDaoImpl.class);

	public Tag findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Tag> findAllBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		return (List<Tag>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Tag> findAllSiteIdAndMatchedTag(long siteId, String tag) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.like("tag", tag));
		return (List<Tag>) criteria.list();
	}

	public Tag findTagBySiteIdAndTag(long siteId, String tag) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.eq("tag", tag));
		return (Tag) criteria.uniqueResult();
	}

	public void save(Tag tag) {
		persist(tag);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		Tag tag = (Tag) criteria.uniqueResult();
		delete(tag);
	}
}
