package com.serpstat.restapi.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.serpstat.restapi.dao.AbstractDao;
import com.serpstat.restapi.dao.UserDao;
import com.serpstat.restapi.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Long, User> implements UserDao {
	static final Logger logger = LoggerFactory.getLogger(SiteDaoImpl.class);

	public User findById(long id) {
		User user = getByKey(id);
		if (user != null) {
			Hibernate.initialize(user.getUserAPIs());
			Hibernate.initialize(user.getSites());
		}
		return user;
	}

	public User findByLogin(String login) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("login", login));
		User user = (User) criteria.uniqueResult();
		if (user != null) {
			Hibernate.initialize(user.getUserAPIs());
			Hibernate.initialize(user.getSites());
		}
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria();
		criteria.addOrder(Order.asc("login"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		List<User> users = (List<User>) criteria.list();
		for (User user : users) {
			Hibernate.initialize(user.getUserAPIs());
			Hibernate.initialize(user.getSites());
		}
		return users;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteByLogin(String login) {
		Criteria criteria = createEntityCriteria();
		criteria.add(Restrictions.eq("login", login));
		User user = (User) criteria.uniqueResult();
		delete(user);
	}
}
