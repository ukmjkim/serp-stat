package com.serpstat.rest.controller;

import static org.junit.Assert.*;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.serpstat.rest.domain.Site;
import com.serpstat.rest.domain.User;
import com.serpstat.rest.domain.UserAPI;
import com.serpstat.rest.repository.UserRepository;
import com.serpstat.rest.web.UserController;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void listAllUsers() throws Exception {
		List<User> mockList = getUserList();

		Mockito.when(userRepository.findAll()).thenReturn(mockList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(userRepository, atLeastOnce()).findAll();
		String jsonString = result.getResponse().getContentAsString();

		List<LinkedHashMap<String, Object>> usersList = new Gson().fromJson(
				jsonString, 
				new TypeToken<List<LinkedHashMap<String, Object>>>() {}.getType());

		System.out.println(usersList);
		if (usersList != null) {
			assertEquals(2, usersList.size());
		}
	}

	@Test
	public void getUser() throws Exception {
		User user = getUserList().get(0);
		Optional<User> mockUser = Optional.of(user);
		
		when(userRepository.findById(anyLong())).thenReturn(mockUser);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/users/"+user.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

		verify(userRepository, atLeastOnce()).findById(anyLong());
		String jsonString = result.getResponse().getContentAsString();

		LinkedHashMap<String, Object> userMap = new Gson().fromJson(
				jsonString, 
				new TypeToken<LinkedHashMap<String, Object>>() {}.getType());

		System.out.println(userMap);
		if (userMap != null) {
			long userId = convertDoubleToLong(userMap, "id");
			assertEquals(1, userId);
		}
	}

	@Test
	public void createUserWithSuccess() {
		User mockUser = new User("login1234", "password", "niceName", "email");

		String userJson = new Gson().toJson(mockUser);

		mockUser.setId(1L);
		when(userRepository.findByLogin(anyString())).thenReturn(Optional.empty());
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(mockUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/")
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.CREATED.value(), response.getStatus());
			assertEquals("http://localhost/users/1", response.getHeader(HttpHeaders.LOCATION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createUserWithConflict() {
		User mockUser = new User("login", "password", "niceName", "email");

		String userJson = new Gson().toJson(mockUser);

		when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(mockUser));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/users/")
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			String jsonString = result.getResponse().getContentAsString();
			LinkedHashMap<String, Object> errorMap = new Gson().fromJson(
					jsonString, 
					new TypeToken<LinkedHashMap<String, Object>>() {}.getType());
			long errorCode = convertDoubleToLong(errorMap, "errorCode");
			assertEquals(HttpStatus.CONFLICT.value(), errorCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateUserWithSuccess() {
		User mockUser = new User("login", "password", "niceName", "email");

		String userJson = new Gson().toJson(mockUser);
		
		mockUser.setId(1L);
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(mockUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/"+mockUser.getId())
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateUserWithNotFound() {
		User mockUser = new User("login", "password", "niceName", "email");
		
		String userJson = new Gson().toJson(mockUser);

		mockUser.setId(1L);
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(mockUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/users/"+mockUser.getId())
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			String jsonString = result.getResponse().getContentAsString();
			LinkedHashMap<String, Object> errorMap = new Gson().fromJson(
					jsonString, 
					new TypeToken<LinkedHashMap<String, Object>>() {}.getType());
			long errorCode = convertDoubleToLong(errorMap, "errorCode");
			assertEquals(HttpStatus.NOT_FOUND.value(), errorCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteUserWithSuccess() {
		User mockUser = new User("login", "password", "niceName", "email");
		mockUser.setId(1L);
		when(userRepository.findById(anyLong())).thenReturn(Optional.of(mockUser));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/"+mockUser.getId())
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(userRepository, atLeastOnce()).deleteById(anyLong());

			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.NO_CONTENT.value(), response.getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteUserWithNotFound() {
		User mockUser = new User("login", "password", "niceName", "email");

		String userJson = new Gson().toJson(mockUser);
		
		mockUser.setId(1L);
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/users/"+mockUser.getId())
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(userRepository, never()).deleteById(anyLong());

			MockHttpServletResponse response = result.getResponse();
			assertEquals(HttpStatus.OK.value(), response.getStatus());
			String jsonString = result.getResponse().getContentAsString();
			LinkedHashMap<String, Object> errorMap = new Gson().fromJson(
					jsonString, 
					new TypeToken<LinkedHashMap<String, Object>>() {}.getType());
			long errorCode = convertDoubleToLong(errorMap, "errorCode");
			assertEquals(HttpStatus.NOT_FOUND.value(), errorCode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long convertDoubleToLong(Map<String, Object> map, String fieldName) {
		double fromNumber = (double) map.get(fieldName);
		return (new Double(fromNumber)).longValue();
	}

	private List<User> getUserList() {
		Set<UserAPI> userApis1 = new HashSet<UserAPI>();
		UserAPI userApi1 = new UserAPI("apiKey1", "ips", 1000, 1000);
		userApi1.setId(1);
		userApis1.add(userApi1);

		Set<Site> sites1 = new HashSet<Site>();
		Site site1 = new Site("title1", "url", 1, 0, 0, "contactEmail", null, null);
		site1.setId(1L);
		User user1 = new User("login", "password", "niceName", "email");
		user1.setId(1L);
		user1.setUserAPIs(userApis1);
		site1.setUser(user1);
		sites1.add(site1);
		user1.setSites(sites1);

		Set<UserAPI> userApis2 = new HashSet<UserAPI>();
		UserAPI userApi2 = new UserAPI("apiKey2", "ips", 1000, 1000);
		userApi2.setId(2);
		userApis2.add(userApi2);

		Set<Site> sites2 = new HashSet<Site>();
		Site site2 = new Site("title2", "url", 1, 0, 0, "contactEmail", null, null);
		site2.setId(2L);
		User user2 = new User("login", "password", "niceName", "email");
		user2.setId(2L);
		user2.setUserAPIs(userApis2);
		site2.setUser(user2);
		sites2.add(site2);
		user2.setSites(sites2);

		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		return users;
	}
}
