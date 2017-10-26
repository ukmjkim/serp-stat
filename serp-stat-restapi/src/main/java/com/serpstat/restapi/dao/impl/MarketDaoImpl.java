package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.MarketDao;
import com.serpstat.restapi.model.Market;

@Repository("marketDao")
public class MarketDaoImpl extends AbstractDao<Integer, Market> implements MarketDao {
	public Market findById(int id) {
		return getByKey(id);
	}

	public Market findByRegionAneLang(String region, String lang) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("region", region));
		criteria.add(Restrictions.eq("lang", lang));
		return (Market) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<Market> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("region"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<Market>) criteria.list();
	}

	public void save(Market market) {
		persist(market);
	}

	public void deleteById(int id) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("id", id));
		Market market = (Market) criteria.uniqueResult();
		delete(market);
	}
}
