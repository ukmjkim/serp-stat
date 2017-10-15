package com.serpstat.restapi.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.UserAPIDao;
import com.serpstat.restapi.model.UserAPI;
import com.serpstat.restapi.service.UserAPIService;

@Service("userAPIService")
@Transactional
public class UserAPIServiceImpl implements UserAPIService {
	static final Logger logger = LoggerFactory.getLogger(UserAPIServiceImpl.class);

	@Autowired
	UserAPIDao dao;

	public UserAPI findById(int id) {
		return dao.findById(id);
	}
	public UserAPI findByKey(String apiKey) {
		return dao.findByKey(apiKey);
	}
	public List<UserAPI> findAllByUserId(long userId) {
		return dao.findAllByUserId(userId);
	}
	public List<UserAPI> findAll() {
		return dao.findAll();
	}
	public void saveUserAPI(UserAPI userApi) {
		dao.save(userApi);
	}
	public void updateUserAPI(UserAPI userApi) {
		UserAPI entity = dao.findById(userApi.getId());
		if (entity != null) {
			entity.setIps(userApi.getIps());
			entity.setCount(userApi.getCount());
			entity.setApiLimit(userApi.getApiLimit());
		}
	}
	public void deleteUserAPI(UserAPI userApi) {
		dao.deleteByKey(userApi.getApiKey());
	}
	public void deleteByKey(String apiKey) {
		dao.deleteByKey(apiKey);
	}
	public boolean isUserAPIKeyUnique(Integer id, String key) {
		UserAPI userApi = dao.findByKey(key);
		return (userApi == null || ((id != null) && userApi.getId() == id));
	}
}
