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
		System.out.println("Testing listAllUsers API-----------");
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI+"/user/",
                HttpMethod.GET, request, List.class);

		List<LinkedHashMap<String, Object>> usersMap = (List<LinkedHashMap<String, Object>>) response.getBody();
		
		if (usersMap != null) {
			for (LinkedHashMap<String, Object> map : usersMap) {
				System.out.println("User : id="+map.get("id")+", Login="+map.get("login")+", Email="+map.get("email"));
			}
		} else {
			System.out.println("No user exist ----------------");
		}
	}

	public static void main(String[] args) {
        listAllUsers();
	}
}
