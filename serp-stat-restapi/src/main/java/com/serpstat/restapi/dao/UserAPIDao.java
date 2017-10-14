package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.UserAPI;

public interface UserAPIDao {
	UserAPI findById(int id);
	UserAPI findByKey(String key);
	List<UserAPI> findAllByUserId(long userId);
	List<UserAPI> findAll();
	void save(UserAPI userApi);
	void delete(UserAPI userApi);
	void deleteByKey(String key);
}
