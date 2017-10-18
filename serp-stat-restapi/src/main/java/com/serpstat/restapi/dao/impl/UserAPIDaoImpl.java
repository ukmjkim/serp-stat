package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.UserAPIDao;
import com.serpstat.restapi.model.UserAPI;

@Repository("userAPIDao")
public class UserAPIDaoImpl extends AbstractDao<Integer, UserAPI> implements UserAPIDao {
	static final Logger logger = LoggerFactory.getLogger(UserAPIDaoImpl.class);

	public UserAPI findById(int id) {
		return getByKey(id);
	}

	public UserAPI findByKey(String apiKey) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("apiKey", apiKey));
		return (UserAPI) criteria.uniqueResult();
	}

	@SuppressWarnings("unchecked")
	public List<UserAPI> findAllByUserId(long userId) {
		Criteria criteria = createEntityCriteria();
		Criteria userCriteria = criteria.createCriteria("user");
		userCriteria.add(Restrictions.eq("id", userId));
		return (List<UserAPI>) criteria.list();
	}

	@SuppressWarnings("unchecked")
	public List<UserAPI> findAll() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("apiKey"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (List<UserAPI>) criteria.list();
	}

	public void save(UserAPI userApi) {
		persist(userApi);
	}

	public void deleteByKey(String apiKey) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("apiKey", apiKey));
		UserAPI userApi = (UserAPI) criteria.uniqueResult();
		delete(userApi);
	}
}
