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
import org.springframework.web.util.UriComponentsBuilder;

import com.serpstat.restapi.model.TagDistrib;

/* Run As > Java Application */
public class TagDistribRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static long lastCreatedId = 0;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings("unchecked")
	private static void getTagDistrib(long tagId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getTagDistrib API----------- with ------ " + tagId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/tag/" + tagId + "/distrib/" + id,
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println(
						"Tag Distrib : id=" + map.get("id") + ", tag_id=" + map.get("tagId") + ", crawl_date=" + map.get("crawlDate"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listTagDistrib(long tagId, int fromDate, int toDate) {
		System.out.println("=======================================================");
		System.out.println("Testing listTagDistrib API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REST_SERVICE_URI + "/tag/" + tagId + "/distrib")
		        .queryParam("fromDate", fromDate)
		        .queryParam("toDate", toDate);

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request, List.class);

		List<LinkedHashMap<String, Object>> tagDistribsMap = response.getBody();

		if (tagDistribsMap != null) {
			System.out.println("count: " + tagDistribsMap.size());
			for (LinkedHashMap<String, Object> map : tagDistribsMap) {
				System.out.println(
						"Tag Distrib : id=" + map.get("id") + ", tag_id=" + map.get("tagId") + ", crawl_date=" + map.get("crawlDate"));
			}
		} else {
			System.out.println("No tag exist ----------------");
		}
	}

	private static void createTagDistrib(long tagId, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing createTagDistrib API----------------- ");

		TagDistrib tagDistrib = new TagDistrib();
		tagDistrib.setTagId(tagId);
		tagDistrib.setCrawlDate(crawlDate);
		Instant instant = Instant.now();
		tagDistrib.setCreatedAt(Date.from(instant));

		System.out.println(tagDistrib);

		HttpEntity<Object> request = new HttpEntity<Object>(tagDistrib, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/tag/" + tagId + "/distrib", HttpMethod.POST, request,
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

	private static void updateTagDistrib(long tagId, long id, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing updateTagDistrib API----------------- ");

		TagDistrib tagDistrib = new TagDistrib();
		tagDistrib.setId(id);
		tagDistrib.setTagId(tagId);
		tagDistrib.setCrawlDate(crawlDate);
		tagDistrib.setOne(100);
		Instant instant = Instant.now();
		tagDistrib.setCreatedAt(Date.from(instant));

		System.out.println(tagDistrib);

		HttpEntity<Object> request = new HttpEntity<Object>(tagDistrib, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/tag/" + tagId + "/distrib/" + id, HttpMethod.PUT, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println(
					"Tag Distrib : id=" + map.get("id") 
					+ ", tag_id=" + map.get("tagId")
					+ ", crawl_date=" + map.get("crawlDate")
					+ ", updated_at=" + map.get("updatedAt"));
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void deleteTagDistrib(long tagId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing deleteTagDistrib API----------------- ");

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/tag/" + tagId + "/distrib/" + id, HttpMethod.DELETE, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	public static void main(String[] args) {
		createTagDistrib(1, 20170101);
		createTagDistrib(1, 20170102);
		listTagDistrib(1, 20170101, 20171231);
		getTagDistrib(1, 1);
		getTagDistrib(1, 2);
		updateTagDistrib(1, 1, 20170101);
		deleteTagDistrib(1, 2);
	}
}
