package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.KeywordDao;
import com.serpstat.restapi.model.Keyword;

@Repository("keywordDao")
public class KeywordDaoImpl extends AbstractDao<Long, Keyword> implements KeywordDao {
	public Keyword findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Keyword> findAllBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.addOrder(Order.asc("title"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Keyword>) criteria.list();
	}

	public Keyword findBySiteIdAndKeyword(long siteId, String keyword) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.add(Restrictions.eq("keyword", keyword));
		return (Keyword) criteria.uniqueResult();
	}

	public void save(Keyword keyword) {
		persist(keyword);
	}

	public void deleteById(long id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		Keyword keyword = (Keyword) criteria.uniqueResult();
		delete(keyword);
	}
}
