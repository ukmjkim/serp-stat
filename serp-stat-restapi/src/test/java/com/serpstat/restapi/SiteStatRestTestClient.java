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

import com.serpstat.restapi.model.SiteStat;

/* Run As > Java Application */
public class SiteStatRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static long lastCreatedId = 0;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings("unchecked")
	private static void getSiteStat(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getSiteStat API----------- with ------ " + siteId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/stat/" + id,
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println(
						"Site Stat : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", crawl_date=" + map.get("crawlDate"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listSiteStat(long siteId, int fromDate, int toDate) {
		System.out.println("=======================================================");
		System.out.println("Testing listSiteStat API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REST_SERVICE_URI + "/site/" + siteId + "/stat")
		        .queryParam("fromDate", fromDate)
		        .queryParam("toDate", toDate);

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request, List.class);

		List<LinkedHashMap<String, Object>> siteStatsMap = response.getBody();

		if (siteStatsMap != null) {
			System.out.println("count: " + siteStatsMap.size());
			for (LinkedHashMap<String, Object> map : siteStatsMap) {
				System.out.println(
						"Site Stat : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", crawl_date=" + map.get("crawlDate"));
			}
		} else {
			System.out.println("No site exist ----------------");
		}
	}

	private static void createSiteStat(long siteId, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing createSiteStat API----------------- ");

		SiteStat siteStat = new SiteStat();
		siteStat.setSiteId(siteId);
		siteStat.setCrawlDate(crawlDate);
		Instant instant = Instant.now();
		siteStat.setCreatedAt(Date.from(instant));

		System.out.println(siteStat);

		HttpEntity<Object> request = new HttpEntity<Object>(siteStat, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/stat", HttpMethod.POST, request,
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

	private static void updateSiteStat(long siteId, long id, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing updateSiteStat API----------------- ");

		SiteStat siteStat = new SiteStat();
		siteStat.setId(id);
		siteStat.setSiteId(siteId);
		siteStat.setCrawlDate(crawlDate);
		siteStat.setTotalKeywords(100);
		Instant instant = Instant.now();
		siteStat.setCreatedAt(Date.from(instant));

		System.out.println(siteStat);

		HttpEntity<Object> request = new HttpEntity<Object>(siteStat, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/stat/" + id, HttpMethod.PUT, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println(
					"Site Stat : id =" + map.get("id") 
					+ ", site_id =" + map.get("siteId")
					+ ", crawl_date =" + map.get("crawlDate")
					+ ", updated_at =" + map.get("updatedAt"));
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void deleteSiteStat(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing deleteSiteStat API----------------- ");

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/stat/" + id, HttpMethod.DELETE, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	public static void main(String[] args) {
		createSiteStat(1, 20170101);
		createSiteStat(1, 20170102);
		listSiteStat(1, 20170101, 20171231);
		getSiteStat(1, 1);
		getSiteStat(1, 2);
		updateSiteStat(1, 1, 20170101);
		deleteSiteStat(1, 1);
	}
}
