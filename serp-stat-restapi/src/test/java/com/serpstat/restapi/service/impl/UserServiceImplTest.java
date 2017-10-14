package com.serpstat.restapi.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.UserDao;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.UserAPI;

public class UserServiceImplTest {
	@Mock
	UserDao dao;

	@InjectMocks
	UserServiceImpl userService;
	
	@Spy
	List<User> users = new ArrayList<User>();
	
	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		users = getUserList();
	}
	
	@Test
	public void findById() {
		User user = users.get(0);
		when(dao.findById(anyLong())).thenReturn(user);
		Assert.assertEquals(userService.findById(user.getId()), user);
	}

	@Test
	public void findByLogin() {
		User user = users.get(0);
		when(dao.findByLogin(anyString())).thenReturn(user);
		Assert.assertEquals(userService.findByLogin(user.getLogin()), user);
	}

	@Test
	public void findAllUsers() {
		when(dao.findAllUsers()).thenReturn(users);
		Assert.assertEquals(userService.findAllUsers(), users);
	}

	@Test
	public void saveUser() {
		doNothing().when(dao).save(any(User.class));
		userService.saveUser((User) any(User.class));
		verify(dao, atLeastOnce()).save(any(User.class));
	}

	@Test
	public void updateUser() {
		User user = users.get(0);
System.out.println("updateUser user: " + user);
		when(dao.findById(anyLong())).thenReturn(user);
		userService.updateUser(user);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteByLogin() {
		doNothing().when(dao).deleteByLogin(anyString());
		userService.deleteByLogin(anyString());
		verify(dao, atLeastOnce()).deleteByLogin(anyString());
	}

	@Test
	public void isUserLoginUnique() {
		User user = users.get(0);
		when(dao.findByLogin(anyString())).thenReturn(user);
		Assert.assertEquals(userService.isUserLoginUnique(user.getId(), user.getLogin()), true);
	}

	public List<User> getUserList() {
		Set<UserAPI> userApis1 = new HashSet<UserAPI>();
		UserAPI userApi1 = new UserAPI();

		userApi1.setId(1);
		userApi1.setKey("newKey1");
		userApis1.add(userApi1);

		User user1 = new User();
		user1.setId(1L);
		user1.setLogin("newLogin");
		user1.setPassword("password");
		user1.setNiceName("nicename");
		user1.setEmail("email");
		user1.setUserAPIs(userApis1);

		Set<UserAPI> userApis2 = new HashSet<UserAPI>();
		UserAPI userApi2 = new UserAPI();

		userApi2.setId(2);
		userApi2.setKey("newKey2");
		userApis2.add(userApi2);

		User user2 = new User();
		user2.setId(2L);
		user2.setLogin("newLogin");
		user2.setPassword("password");
		user2.setNiceName("nicename");
		user2.setEmail("email");
		user2.setUserAPIs(userApis2);
		
		users.add(user1);
		users.add(user2);
		return users;
	}
}
