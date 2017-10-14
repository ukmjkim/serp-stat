package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.User;

public interface UserService {
	User findById(long id);
	User findByLogin(String login);
	List<User> findAllUsers();
	void saveUser(User user);
	void updateUser(User user);
	void deleteByLogin(String login);
	boolean isUserLoginUnique(Long id, String login);
}
