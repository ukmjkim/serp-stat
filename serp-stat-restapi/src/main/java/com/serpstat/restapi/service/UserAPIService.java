package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.UserAPI;

public interface UserAPIService {
	UserAPI findById(int id);
	UserAPI findByKey(String apiKey);
	List<UserAPI> findAllByUserId(long userId);
	List<UserAPI> findAll();
	void saveUserAPI(UserAPI userApi);
	void updateUserAPI(UserAPI userApi);
	void deleteUserAPI(UserAPI userApi);
	void deleteByKey(String apiKey);
	boolean isUserAPIKeyUnique(Integer id, String apiKey);
}
