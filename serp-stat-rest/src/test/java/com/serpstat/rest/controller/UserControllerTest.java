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
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.serpstat.rest.controller.UserController;
import com.serpstat.rest.domain.Site;
import com.serpstat.rest.domain.User;
import com.serpstat.rest.domain.UserAPI;
import com.serpstat.rest.repository.UserRepository;

import static com.serpstat.rest.utils.JsonConverter.convertObjectToJson;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToList;
import static com.serpstat.rest.utils.JsonConverter.convertJsonToMap;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure = false)
public class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private UserRepository userRepository;

	@Test
	public void listAllUsers() throws Exception {
		List<User> userList = getUserList();

		when(userRepository.findAll()).thenReturn(userList);

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/users/").accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(userRepository, atLeastOnce()).findAll();
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		List<Map<String, Object>> list = convertJsonToList(result.getResponse().getContentAsString());
		assertEquals(2, list.size());
	}

	@Test
	public void getUser() throws Exception {
		User user = getUserList().get(0);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

		MvcResult result = mockMvc
				.perform(MockMvcRequestBuilders.get("/api/v1/users/"+user.getId()).accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		verify(userRepository, atLeastOnce()).findById(anyLong());
		assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

		Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());

		assertEquals(1,  map.get("id"));
	}

	@Test
	public void createUserWithSuccess() {
		User newUser = getNewUser();
		String userJson = convertObjectToJson(newUser);

		when(userRepository.findByLogin(anyString())).thenReturn(Optional.empty());
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(newUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/")
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.CREATED.value(), result.getResponse().getStatus());
			assertEquals("http://localhost/api/v1/users/1", result.getResponse().getHeader(HttpHeaders.LOCATION));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void createUserWithConflict() {
		User newUser = getNewUser();
		String userJson = convertObjectToJson(newUser);

		when(userRepository.findByLogin(anyString())).thenReturn(Optional.of(newUser));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/api/v1/users/")
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.CONFLICT.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateUserWithSuccess() {
		User newUser = getNewUser();
		String userJson = convertObjectToJson(newUser);

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(newUser));
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(newUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/users/"+newUser.getId())
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void updateUserWithNotFound() {
		User newUser = getNewUser();
		String userJson = convertObjectToJson(newUser);

		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
		when(userRepository.saveAndFlush(any(User.class))).thenReturn(newUser);

		RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/api/v1/users/"+newUser.getId())
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteUserWithSuccess() {
		User newUser = getNewUser();

		when(userRepository.findById(anyLong())).thenReturn(Optional.of(newUser));

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/"+newUser.getId())
				.accept(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(userRepository, atLeastOnce()).deleteById(anyLong());

			assertEquals(HttpStatus.NO_CONTENT.value(), result.getResponse().getStatus());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void deleteUserWithNotFound() {
		User newUser = getNewUser();
		String userJson = convertObjectToJson(newUser);
		
		when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/api/v1/users/"+newUser.getId())
				.accept(MediaType.APPLICATION_JSON).content(userJson).contentType(MediaType.APPLICATION_JSON);

		MvcResult result;
		try {
			result = mockMvc.perform(requestBuilder).andReturn();
			verify(userRepository, never()).deleteById(anyLong());
			assertEquals(HttpStatus.OK.value(), result.getResponse().getStatus());

			Map<String, Object> map = convertJsonToMap(result.getResponse().getContentAsString());
			assertEquals(HttpStatus.NOT_FOUND.value(), ((Integer) map.get("errorCode")).intValue());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User getNewUser() {
		User newUser = new User("login", "password", "niceName", "email");
		newUser.setId(1L);
		return newUser;
	}

	private List<User> getUserList() {
		Set<UserAPI> userApis1 = new HashSet<UserAPI>();
		UserAPI userApi1 = new UserAPI("apiKey1", "ips", 1000, 1000);
		userApi1.setId(1);
		userApis1.add(userApi1);

		User user1 = new User("login", "password", "niceName", "email");
		user1.setId(1L);
		user1.setUserAPIs(userApis1);

		Set<UserAPI> userApis2 = new HashSet<UserAPI>();
		UserAPI userApi2 = new UserAPI("apiKey2", "ips", 1000, 1000);
		userApi2.setId(2);
		userApis2.add(userApi2);

		User user2 = new User("login", "password", "niceName", "email");
		user2.setId(2L);
		user2.setUserAPIs(userApis2);

		List<User> users = new ArrayList<>();
		users.add(user1);
		users.add(user2);
		return users;
	}
}
