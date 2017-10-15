package com.serpstat.restapi.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.UserAPI;
import com.serpstat.restapi.service.UserService;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.UserAPIService;

public class UserRestControllerTest {
	@Mock
	UserService userService;

	@Mock
	UserAPIService userApiService;

	@Mock
	SiteService siteService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	UserRestController userController;

	@Spy
	List<User> users = new ArrayList<User>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		users = getUserList();
	}
	
	@Test
	public void listUsers() {
		when(userService.findAllUsers()).thenReturn(users);
		ResponseEntity<List<User>> response = userController.listAllUsers();
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		verify(userService, atLeastOnce()).findAllUsers();
	}
	
	@Test
	public void getUser() {
		User user = users.get(0);
		when(userService.findById(anyLong())).thenReturn(user);
		ResponseEntity<User> response = userController.getUser(user.getId());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		verify(userService, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void createUserWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(userService).saveUser(any(User.class));
		when(userService.isUserLoginUnique(anyLong(), anyString())).thenReturn(true);
		ResponseEntity<Void> response = userController.createUser(users.get(0), ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void createUserWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(userService).saveUser(any(User.class));
		when(userService.isUserLoginUnique(anyLong(), anyString())).thenReturn(false);
		ResponseEntity<Void> response = userController.createUser(users.get(0), ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}

	@Test
	public void updateUserWithNotFound() {
		doNothing().when(userService).saveUser(any(User.class));
		when(userService.findById(anyLong())).thenReturn(null);
		ResponseEntity<User> response = userController.updateUser(1000L, users.get(0));
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void updateUserWithSuccess() {
		User user = users.get(0);
		doNothing().when(userService).saveUser(any(User.class));
		when(userService.findById(anyLong())).thenReturn(user);
		ResponseEntity<User> response = userController.updateUser(user.getId(), user);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deleteUserWithNotFound() {
		doNothing().when(userService).deleteByLogin(anyString());
		when(userService.findById(anyLong())).thenReturn(null);
		ResponseEntity<User> response = userController.deleteUser(1000L);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void deleteUserWithSuccess() {
		User user = users.get(0);
		doNothing().when(userService).deleteByLogin(anyString());
		when(userService.findById(anyLong())).thenReturn(user);
		ResponseEntity<User> response = userController.deleteUser(user.getId());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	@Test
	public void addUserAPIWithUserNotFound() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		Set<UserAPI> userApis = users.get(0).getUserAPIs();
		UserAPI userApi = userApis.iterator().next();
		when(userService.findById(anyLong())).thenReturn(null);
		doNothing().when(userApiService).saveUserAPI(any(UserAPI.class));
		ResponseEntity<Void> response = userController.addUserAPI(users.get(0).getId(), userApi, ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void addUserAPIWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		User user = users.get(0);
		Set<UserAPI> userApis = users.get(0).getUserAPIs();
		UserAPI userApi = userApis.iterator().next();
		when(userService.findById(anyLong())).thenReturn(user);
		doNothing().when(userApiService).saveUserAPI(any(UserAPI.class));
		ResponseEntity<Void> response = userController.addUserAPI(user.getId(), userApi, ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}

	@Test
	public void updateUserAPIWithNotFound() {
		Set<UserAPI> userApis = users.get(0).getUserAPIs();
		UserAPI userApi = userApis.iterator().next();
		when(userApiService.findById(anyInt())).thenReturn(null);
		doNothing().when(userApiService).updateUserAPI(any(UserAPI.class));
		ResponseEntity<UserAPI> response = userController.updateUserAPI(users.get(0).getId(), userApi.getId(), userApi);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void updateUserAPIWithSuccess() {
		Set<UserAPI> userApis = users.get(0).getUserAPIs();
		UserAPI userApi = userApis.iterator().next();
		when(userApiService.findById(anyInt())).thenReturn(userApi);
		doNothing().when(userApiService).updateUserAPI(any(UserAPI.class));
		ResponseEntity<UserAPI> response = userController.updateUserAPI(users.get(0).getId(), userApi.getId(), userApi);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deleteUserAPIWithNotFound() {
		Set<UserAPI> userApis = users.get(0).getUserAPIs();
		UserAPI userApi = userApis.iterator().next();
		when(userApiService.findById(anyInt())).thenReturn(null);
		doNothing().when(userApiService).deleteUserAPI(any(UserAPI.class));
		ResponseEntity<UserAPI> response = userController.deleteUserAPI(users.get(0).getId(), userApi.getId());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void deleteUserAPIWithSuccess() {
		Set<UserAPI> userApis = users.get(0).getUserAPIs();
		UserAPI userApi = userApis.iterator().next();
		when(userApiService.findById(anyInt())).thenReturn(userApi);
		doNothing().when(userApiService).deleteUserAPI(any(UserAPI.class));
		ResponseEntity<UserAPI> response = userController.deleteUserAPI(users.get(0).getId(), userApi.getId());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	@Test
	public void addSiteWithUserNotFound() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).saveSite(any(Site.class));
		when(userService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Void> response = userController.addSite(user.getId(), site, ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void addSiteWithSiteConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).saveSite(any(Site.class));
		when(userService.findById(anyLong())).thenReturn(user);
		when(siteService.isSiteTitleUnique(anyLong(), anyLong(), anyString())).thenReturn(true);
		ResponseEntity<Void> response = userController.addSite(user.getId(), site, ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
	}

	@Test
	public void addSiteWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).saveSite(any(Site.class));
		when(userService.findById(anyLong())).thenReturn(user);
		when(siteService.isSiteTitleUnique(anyLong(), anyLong(), anyString())).thenReturn(false);
		ResponseEntity<Void> response = userController.addSite(user.getId(), site, ucBuilder);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.CREATED);
	}

	@Test
	public void updateSiteWithNotFound() {
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).updateSite(any(Site.class));
		when(siteService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Site> response = userController.updateSite(user.getId(), site.getId(), site);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void updateSiteWithSuccess() {
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).updateSite(any(Site.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		ResponseEntity<Site> response = userController.updateSite(user.getId(), site.getId(), site);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
	}

	@Test
	public void deleteSiteWithNotFound() {
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).deleteById(anyLong());
		when(siteService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Site> response = userController.deleteSite(user.getId(), site.getId());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@Test
	public void deleteSiteWithSuccess() {
		User user = users.get(0);
		Set<Site> sites = users.get(0).getSites();
		Site site = sites.iterator().next();
		doNothing().when(siteService).deleteById(anyLong());
		when(siteService.findById(anyLong())).thenReturn(site);
		ResponseEntity<Site> response = userController.deleteSite(user.getId(), site.getId());
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
	}

	public List<User> getUserList() {
		Set<UserAPI> userApis1 = new HashSet<UserAPI>();
		UserAPI userApi1 = new UserAPI();
		userApi1.setId(1);
		userApi1.setApiKey("newKey1");
		userApis1.add(userApi1);

		Set<Site> sites1 = new HashSet<Site>();
		Site site1 = new Site();
		site1.setId(1L);
		site1.setTitle("newTitle1");
		site1.setUrl("www.url1.com");

		User user1 = new User();
		user1.setId(1L);
		user1.setLogin("newLogin");
		user1.setPassword("password");
		user1.setNiceName("nicename");
		user1.setEmail("email");
		user1.setUserAPIs(userApis1);
		site1.setUser(user1);
		sites1.add(site1);
		user1.setSites(sites1);

		Set<UserAPI> userApis2 = new HashSet<UserAPI>();
		UserAPI userApi2 = new UserAPI();
		userApi2.setId(2);
		userApi2.setApiKey("newKey2");
		userApis2.add(userApi2);


		Set<Site> sites2 = new HashSet<Site>();
		Site site2 = new Site();
		site2.setId(1L);
		site2.setTitle("newTitle1");
		site2.setUrl("www.url1.com");

		User user2 = new User();
		user2.setId(2L);
		user2.setLogin("newLogin");
		user2.setPassword("password");
		user2.setNiceName("nicename");
		user2.setEmail("email");
		user2.setUserAPIs(userApis2);
		site2.setUser(user2);
		sites2.add(site2);
		user2.setSites(sites2);

		users.add(user1);
		users.add(user2);
		return users;
	}
}
