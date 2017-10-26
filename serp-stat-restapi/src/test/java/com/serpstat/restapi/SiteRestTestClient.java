package com.serpstat.restapi;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.RestTemplate;

/* Run As > Java Application */
public class SiteRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static final String FILE_NAME = "file/keyword.csv";

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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

	@SuppressWarnings("rawtypes")
	private static void listAllTagsBySiteId(long siteId) {
		System.out.println("=======================================================");
		System.out.println("Testing listAllTagsBySiteId API-----------");
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Map> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/tags",
				HttpMethod.GET, request, Map.class);

		@SuppressWarnings("unchecked")
		LinkedHashMap<String, Object> tagsMap = (LinkedHashMap<String, Object>) response.getBody();

		System.out.println(tagsMap);

		if (tagsMap != null) {
			System.out.println("Site : id=" + tagsMap.get("id") + ", title=" + tagsMap.get("title"));
			@SuppressWarnings("unchecked")
			List<LinkedHashMap<String, Object>> tagList = (List<LinkedHashMap<String, Object>>) tagsMap.get("tags");
			for (LinkedHashMap<String, Object> map : tagList) {
				System.out.println("User : id=" + map.get("id") + ", tag=" + map.get("tag"));
			}
		} else {
			System.out.println("No tag exist ----------------");
		}
	}

	private static void uploadKeywords(long siteId) {
		System.out.println("=======================================================");
		System.out.println("---- Testing uploadKeyword API----------------- ");

		LinkedMultiValueMap<String, Object> multiValueMap = new LinkedMultiValueMap<>();
		multiValueMap.add("file", new ClassPathResource(FILE_NAME));
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		HttpEntity<Object> request = new HttpEntity<Object>(multiValueMap, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(
				REST_SERVICE_URI + "/site/" + siteId + "/keyword/upload", HttpMethod.POST, request, Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			System.out.println("keyword uploaded");
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	public static void main(String[] args) {
		// listAllSites();
		// getSite(1);
		// getSite(100);
		// listAllTagsBySiteId(1);
		uploadKeywords(1);
	}
}
