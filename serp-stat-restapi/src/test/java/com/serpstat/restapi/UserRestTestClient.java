package com.serpstat.restapi;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

/* Run As > Java Application */
public class UserRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static int lastCreatedId = 0;
	
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
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/user/",
                HttpMethod.GET, request, List.class);

		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();

		if (usersMap != null) {
			System.out.println("count: " + usersMap.size());
			for (LinkedHashMap<String, Object> map : usersMap) {
				System.out.println("User : id="+map.get("id")+", login="+map.get("login")+", email="+map.get("email"));
				Object objUserAPI = map.get("userAPIs");
				if ((objUserAPI != null) && (objUserAPI instanceof List)) {
					List<LinkedHashMap<String, Object>> userAPIs = (List<LinkedHashMap<String, Object>>) objUserAPI;
					for (LinkedHashMap<String, Object> userAPI : userAPIs) {
						System.out.println("===> UserAPI : id="+userAPI.get("id")+", apiKey="+userAPI.get("apiKey"));
					}
				}
				Object objSites = map.get("sites");
				if ((objSites != null) && (objSites instanceof List)) {
					List<LinkedHashMap<String, Object>> sites = (List<LinkedHashMap<String, Object>>) objSites;
					for (LinkedHashMap<String, Object> site : sites) {
						System.out.println("===> Site : id="+site.get("id")+", title="+site.get("title"));
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
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI+"/user/" + userId + "/", HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println("User : id="+map.get("id")+", login="+map.get("login")+", email="+map.get("email"));
			} else {
				System.out.println("errorCode="+map.get("errorCode")+", message="+map.get("message"));
			}
		}
	}

	public static void main(String[] args) {
        listAllUsers();
        getUser(1);
        getUser(100);
	}
}
