package com.serpstat.restapi;

import java.net.URI;
import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.UserAPI;

/* Run As > Java Application */
public class UserRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static long lastCreatedId = 0;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listAllUsers() {
		System.out.println("=======================================================");
		System.out.println("Testing listAllUsers API-----------");
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "/user/", HttpMethod.GET, request,
				List.class);

		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();

		if (usersMap != null) {
			System.out.println("count: " + usersMap.size());
			for (LinkedHashMap<String, Object> map : usersMap) {
				System.out.println(
						"User : id=" + map.get("id") + ", login=" + map.get("login") + ", email=" + map.get("email"));
				Object objUserAPI = map.get("userAPIs");
				if ((objUserAPI != null) && (objUserAPI instanceof List)) {
					List<LinkedHashMap<String, Object>> userAPIs = (List<LinkedHashMap<String, Object>>) objUserAPI;
					for (LinkedHashMap<String, Object> userAPI : userAPIs) {
						System.out.println(
								"===> UserAPI : id=" + userAPI.get("id") + ", apiKey=" + userAPI.get("apiKey"));
					}
				}
				Object objSites = map.get("sites");
				if ((objSites != null) && (objSites instanceof List)) {
					List<LinkedHashMap<String, Object>> sites = (List<LinkedHashMap<String, Object>>) objSites;
					for (LinkedHashMap<String, Object> site : sites) {
						System.out.println("===> Site : id=" + site.get("id") + ", title=" + site.get("title"));
					}
				}
			}
		} else {
			System.out.println("No user exist ----------------");
		}
	}

	@SuppressWarnings("unchecked")
	private static void getUser(long userId) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getUser API----------- with ------ " + userId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/user/" + userId + "/",
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println(
						"User : id=" + map.get("id") + ", login=" + map.get("login") + ", email=" + map.get("email"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	private static void createUser() {
		System.out.println("=======================================================");
		System.out.println("---- Testing createUser API----------------- ");

		User user = new User();
		user.setLogin("sarah");
		user.setNiceName("Sarah");
		user.setPassword("password");
		user.setEmail("sarah@gmail.com");
		Instant instant = Instant.now();
		user.setCreatedAt(Date.from(instant));
		user.setDeleted(0);

		System.out.println(user);

		HttpEntity<Object> request = new HttpEntity<Object>(user, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/user/", HttpMethod.POST, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			HttpHeaders headers = response.getHeaders();
			URI uri = headers.getLocation();
			String urlStr = uri.toASCIIString();
			System.out.println("Location: " + urlStr);
			String[] paths = urlStr.split("/");
			lastCreatedId = Integer.parseInt(paths[paths.length - 1]);
			System.out.println("lastCreatedId: " + lastCreatedId);
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void addUserAPI() {
		System.out.println("=======================================================");
		System.out.println("---- Testing addUserAPI API----------------- ");

		UserAPI userAPI = new UserAPI();
		userAPI.setApiKey("b7fa7429eb36a6335b714ecabfa1f84dccafce1b");
		userAPI.setIps("");
		userAPI.setCount(0);
		userAPI.setApiLimit(1000);
		Instant instant = Instant.now();
		java.sql.Date date = new java.sql.Date(Date.from(instant).getTime());
		userAPI.setCreatedAt(date);
		userAPI.setDeleted(0);

		System.out.println(userAPI);

		HttpEntity<Object> request = new HttpEntity<Object>(userAPI, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(
				REST_SERVICE_URI + "/user/" + lastCreatedId + "/userapi", HttpMethod.POST, request, Object.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			HttpHeaders headers = response.getHeaders();
			URI uri = headers.getLocation();
			String urlStr = uri.toASCIIString();
			System.out.println("Location: " + urlStr);
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void addSite() {
		System.out.println("=======================================================");
		System.out.println("---- Testing addSite API----------------- ");

		Site site = new Site();
		site.setTitle("wayfair.ca");
		site.setUrl("www.wayfair.ca");
		site.setTracking(1);
		site.setDropWWWPrefix(1);
		site.setDropDirectories(0);
		site.setContactEmail("admin@wayfair.ca");
		Instant instant = Instant.now();
		java.sql.Date date = new java.sql.Date(Date.from(instant).getTime());
		site.setCreatedAt(date);
		site.setDeleted(0);

		System.out.println(site);

		HttpEntity<Object> request = new HttpEntity<Object>(site, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/user/" + lastCreatedId + "/site",
				HttpMethod.POST, request, Object.class);
		if (response.getStatusCode() == HttpStatus.CREATED) {
			HttpHeaders headers = response.getHeaders();
			URI uri = headers.getLocation();
			String urlStr = uri.toASCIIString();
			System.out.println("Location: " + urlStr);
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	public static void main(String[] args) {
		listAllUsers();
		getUser(1);
		getUser(100);
		createUser();
		addUserAPI();
		addSite();
	}
}
