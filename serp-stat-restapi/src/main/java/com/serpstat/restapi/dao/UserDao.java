package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.User;

public interface UserDao {
	User findById(long id);

	User findByLogin(String login);

	List<User> findAllUsers();

	void save(User user);

	void deleteByLogin(String login);
}
