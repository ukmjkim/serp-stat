package com.serpstat.restapi.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.UserAPIDao;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.UserAPI;

public class UserAPIServiceImplTest {
	@Mock
	UserAPIDao dao;

	@InjectMocks
	UserAPIServiceImpl userApiService;
	
	@Spy
	List<UserAPI> userApis = new ArrayList<UserAPI>();
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		userApis = getUserAPIList();
	}

	@Test
	public void findById() {
		UserAPI userApi = userApis.get(0);
		when(dao.findById(anyInt())).thenReturn(userApi);
		Assert.assertEquals(userApiService.findById(userApi.getId()), userApi);
	}

	@Test
	public void findByKey() {
		UserAPI userApi = userApis.get(0);
		when(dao.findByKey(anyString())).thenReturn(userApi);
		Assert.assertEquals(userApiService.findByKey(userApi.getApiKey()), userApi);
	}

	@Test
	public void findAllByUserId() {
		when(dao.findAllByUserId(anyLong())).thenReturn(userApis);
		Assert.assertEquals(userApiService.findAllByUserId(userApis.get(0).getUser().getId()).size(), userApis.size());
	}

	@Test
	public void findAll() {
		when(dao.findAll()).thenReturn(userApis);
		Assert.assertEquals(userApiService.findAll(), userApis);
	}

	@Test
	public void saveUserAPI() {
		doNothing().when(dao).save(any(UserAPI.class));
		userApiService.saveUserAPI((UserAPI) any(UserAPI.class));
		verify(dao, atLeastOnce()).save(any(UserAPI.class));
	}

	@Test
	public void updateUserAPI() {
		UserAPI userApi = userApis.get(0);
		when(dao.findById(anyInt())).thenReturn(userApi);
		userApiService.updateUserAPI(userApi);
		verify(dao, atLeastOnce()).findById(anyInt());
	}

	@Test
	public void deleteByKey() {
		doNothing().when(dao).deleteByKey(anyString());
		userApiService.deleteByKey(anyString());
		verify(dao, atLeastOnce()).deleteByKey(anyString());
	}

	@Test
	public void isUserAPIKeyUnique() {
		UserAPI user = userApis.get(0);
		when(dao.findByKey(anyString())).thenReturn(user);
		Assert.assertEquals(userApiService.isUserAPIKeyUnique(user.getId(), user.getApiKey()), true);
	}

	public List<UserAPI> getUserAPIList() {
		User user = new User();
		user.setId(1L);
		user.setLogin("newLogin");
		user.setPassword("password");
		user.setNiceName("nicename");
		user.setEmail("email");

		List<UserAPI> userApis = new ArrayList<UserAPI>();

		UserAPI userApi1 = new UserAPI();
		userApi1.setId(1);
		userApi1.setUser(user);
		userApi1.setApiKey("newKey1");

		UserAPI userApi2 = new UserAPI();
		userApi2.setId(2);
		userApi1.setUser(user);
		userApi2.setApiKey("newKey2");
		
		userApis.add(userApi1);
		userApis.add(userApi2);
		return userApis;
	}
}
