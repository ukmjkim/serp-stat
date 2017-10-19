package com.serpstat.restapi;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/* Run As > Java Application */
public class SiteRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings("unchecked")
	private static void listAllSites() {
		System.out.println("=======================================================");
		System.out.println("Testing listAllSites API-----------");
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "/site/", HttpMethod.GET, request,
				List.class);

		List<LinkedHashMap<String, Object>> sitesMap = response.getBody();

		if (sitesMap != null) {
			System.out.println("count: " + sitesMap.size());
			for (LinkedHashMap<String, Object> map : sitesMap) {
				System.out.println(
						"User : id=" + map.get("id") + ", title=" + map.get("title") + ", url=" + map.get("url"));
			}
		} else {
			System.out.println("No site exist ----------------");
		}
	}

	@SuppressWarnings("unchecked")
	private static void getSite(long siteId) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getSite API----------- with ------ " + siteId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/",
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println(
						"User : id=" + map.get("id") + ", title=" + map.get("title") + ", url=" + map.get("url"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	private static void listAllTagsBySiteId(long siteId) {
		System.out.println("=======================================================");
		System.out.println("Testing listAllTagsBySiteId API-----------");
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/tags",
				HttpMethod.GET, request, List.class);

		@SuppressWarnings("unchecked")
		List<LinkedHashMap<String, Object>> tagsMap = response.getBody();

		if (tagsMap != null) {
			System.out.println("count: " + tagsMap.size());
			for (LinkedHashMap<String, Object> map : tagsMap) {
				System.out.println("User : id=" + map.get("id") + ", tag=" + map.get("tag"));
			}
		} else {
			System.out.println("No tag exist ----------------");
		}
	}

	public static void main(String[] args) {
		listAllSites();
		getSite(1);
		getSite(100);
		listAllTagsBySiteId(1);
	}
}
