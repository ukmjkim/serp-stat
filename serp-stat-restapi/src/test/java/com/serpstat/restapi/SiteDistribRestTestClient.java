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

import com.serpstat.restapi.model.SiteDistrib;

/* Run As > Java Application */
public class SiteDistribRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static long lastCreatedId = 0;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings("unchecked")
	private static void getSiteDistrib(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getSiteDistrib API----------- with ------ " + siteId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/distrib/" + id,
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println(
						"Site Distrib : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", crawl_date=" + map.get("crawlDate"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listSiteDistrib(long siteId, int fromDate, int toDate) {
		System.out.println("=======================================================");
		System.out.println("Testing listSiteDistrib API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REST_SERVICE_URI + "/site/" + siteId + "/distrib")
		        .queryParam("fromDate", fromDate)
		        .queryParam("toDate", toDate);

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request, List.class);

		List<LinkedHashMap<String, Object>> siteDistribsMap = response.getBody();

		if (siteDistribsMap != null) {
			System.out.println("count: " + siteDistribsMap.size());
			for (LinkedHashMap<String, Object> map : siteDistribsMap) {
				System.out.println(
						"Site Distrib : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", crawl_date=" + map.get("crawlDate"));
			}
		} else {
			System.out.println("No site exist ----------------");
		}
	}

	private static void createSiteDistrib(long siteId, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing createSiteDistrib API----------------- ");

		SiteDistrib siteDistrib = new SiteDistrib();
		siteDistrib.setSiteId(siteId);
		siteDistrib.setCrawlDate(crawlDate);
		Instant instant = Instant.now();
		siteDistrib.setCreatedAt(Date.from(instant));

		System.out.println(siteDistrib);

		HttpEntity<Object> request = new HttpEntity<Object>(siteDistrib, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/distrib", HttpMethod.POST, request,
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

	private static void updateSiteDistrib(long siteId, long id, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing updateSiteDistrib API----------------- ");

		SiteDistrib siteDistrib = new SiteDistrib();
		siteDistrib.setId(id);
		siteDistrib.setSiteId(siteId);
		siteDistrib.setCrawlDate(crawlDate);
		siteDistrib.setOne(100);
		Instant instant = Instant.now();
		siteDistrib.setCreatedAt(Date.from(instant));

		System.out.println(siteDistrib);

		HttpEntity<Object> request = new HttpEntity<Object>(siteDistrib, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/distrib/" + id, HttpMethod.PUT, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println(
					"Site Distrib : id =" + map.get("id") 
					+ ", site_id =" + map.get("siteId")
					+ ", crawl_date =" + map.get("crawlDate")
					+ ", updated_at =" + map.get("updatedAt"));
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void deleteSiteDistrib(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing deleteSiteDistrib API----------------- ");

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/distrib/" + id, HttpMethod.DELETE, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	public static void main(String[] args) {
		createSiteDistrib(1, 20170101);
		createSiteDistrib(1, 20170102);
		listSiteDistrib(1, 20170101, 20171231);
		getSiteDistrib(1, 1);
		getSiteDistrib(1, 2);
		updateSiteDistrib(1, 1, 20170101);
		deleteSiteDistrib(1, 2);
	}
}
