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

import com.serpstat.restapi.model.SiteSearchVolume;

/* Run As > Java Application */
public class SiteSearchVolumeRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static long lastCreatedId = 0;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings("unchecked")
	private static void getSiteSearchVolume(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getSiteSearchVolume API----------- with ------ " + siteId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/searchvolume/" + id,
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println(
						"Site Search Volume : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", crawl_date=" + map.get("crawlDate"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listSiteSearchVolume(long siteId, int fromDate, int toDate) {
		System.out.println("=======================================================");
		System.out.println("Testing listSiteSearchVolume API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(REST_SERVICE_URI + "/site/" + siteId + "/searchvolume")
		        .queryParam("fromDate", fromDate)
		        .queryParam("toDate", toDate);

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request, List.class);

		List<LinkedHashMap<String, Object>> siteSearchVolumesMap = response.getBody();

		if (siteSearchVolumesMap != null) {
			System.out.println("count: " + siteSearchVolumesMap.size());
			for (LinkedHashMap<String, Object> map : siteSearchVolumesMap) {
				System.out.println(
						"Site Search Volume : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", crawl_date=" + map.get("crawlDate"));
			}
		} else {
			System.out.println("No site exist ----------------");
		}
	}

	private static void createSiteSearchVolume(long siteId, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing createSiteSearchVolume API----------------- ");

		SiteSearchVolume siteSearchVolume = new SiteSearchVolume();
		siteSearchVolume.setSiteId(siteId);
		siteSearchVolume.setCrawlDate(crawlDate);
		Instant instant = Instant.now();
		siteSearchVolume.setCreatedAt(Date.from(instant));

		System.out.println(siteSearchVolume);

		HttpEntity<Object> request = new HttpEntity<Object>(siteSearchVolume, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/searchvolume", HttpMethod.POST, request,
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

	private static void updateSiteSearchVolume(long siteId, long id, int crawlDate) {
		System.out.println("=======================================================");
		System.out.println("---- Testing updateSiteSearchVolume API----------------- ");

		SiteSearchVolume siteSearchVolume = new SiteSearchVolume();
		siteSearchVolume.setId(id);
		siteSearchVolume.setSiteId(siteId);
		siteSearchVolume.setCrawlDate(crawlDate);
		siteSearchVolume.setGlobalAggregateSV(100L);
		Instant instant = Instant.now();
		siteSearchVolume.setCreatedAt(Date.from(instant));

		System.out.println(siteSearchVolume);

		HttpEntity<Object> request = new HttpEntity<Object>(siteSearchVolume, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/searchvolume/" + id, HttpMethod.PUT, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println(
					"Site Search Volume : id =" + map.get("id") 
					+ ", site_id =" + map.get("siteId")
					+ ", crawl_date =" + map.get("crawlDate")
					+ ", updated_at =" + map.get("updatedAt"));
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void deleteSiteSearchVolume(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing deleteSiteSearchVolume API----------------- ");

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/searchvolume/" + id, HttpMethod.DELETE, request,
				Object.class);
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	public static void main(String[] args) {
		createSiteSearchVolume(1, 20170101);
		createSiteSearchVolume(1, 20170102);
		listSiteSearchVolume(1, 20170101, 20171231);
		getSiteSearchVolume(1, 1);
		getSiteSearchVolume(1, 2);
		updateSiteSearchVolume(1, 1, 20170101);
		deleteSiteSearchVolume(1, 2);
	}
}
