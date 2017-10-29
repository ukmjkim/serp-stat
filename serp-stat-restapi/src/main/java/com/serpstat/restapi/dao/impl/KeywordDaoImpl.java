package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.ScrollableResults;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.KeywordDao;
import com.serpstat.restapi.model.Keyword;

@Repository("keywordDao")
public class KeywordDaoImpl extends AbstractDao<Long, Keyword> implements KeywordDao {
	static final Logger logger = LoggerFactory.getLogger(KeywordDaoImpl.class);

	public Keyword findById(long id) {
		return getByKey(id);
	}

	@SuppressWarnings("unchecked")
	public List<Keyword> findAllBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.addOrder(Order.asc("keyword"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Keyword>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<Keyword> findPagenatedBySiteId(long siteId, int firstResult, int maxResults) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		criteria.addOrder(Order.asc("keyword"));
		criteria.setFirstResult(firstResult);
		criteria.setMaxResults(maxResults);
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<Keyword> keywords = (List<Keyword>) criteria.list();
		return keywords;
	}

	public int findTotalCountBySiteId(long siteId) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("siteId", siteId));
		ScrollableResults scrollableResults = criteria.scroll();
		scrollableResults.last();
		int totalRecords = scrollableResults.getRowNumber() + 1;
		return totalRecords;
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
