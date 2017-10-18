package com.serpstat.restapi.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.UserDao;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserDao dao;

	public User findById(long id) {
		return dao.findById(id);
	}

	public User findByLogin(String login) {
		return dao.findByLogin(login);
	}

	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	public void saveUser(User user) {
		dao.save(user);
	}

	public void updateUser(User user) {
		User entity = dao.findById(user.getId());
		if (entity != null) {
			entity.setPassword(user.getPassword());
			entity.setNiceName(user.getNiceName());
			entity.setEmail(user.getEmail());
		}
	}

	public void deleteByLogin(String login) {
		dao.deleteByLogin(login);
	}

	public boolean isUserLoginUnique(Long id, String login) {
		User user = dao.findByLogin(login);
		return (user == null || ((id != null) && user.getId() == id));
	}
}
