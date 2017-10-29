package com.serpstat.restapi;

import java.net.URI;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.serpstat.restapi.model.Device;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.model.Market;

/* Run As > Java Application */
public class KeywordRestTestClient {
	public static final String REST_SERVICE_URI = "http://localhost:8080/serp-stat-restapi";
	private static long lastCreatedId = 0;

	private static HttpHeaders getHeaders() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		return headers;
	}

	@SuppressWarnings("unchecked")
	private static void getKeyword(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing getKeyword API----------- with ------ " + siteId);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/keyword/" + id,
				HttpMethod.GET, request, Object.class);
		LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
		if (map != null) {
			if (map.containsKey("id")) {
				System.out.println("Keyword : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", keyword="
						+ map.get("keyword"));
			} else {
				System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listKeyword(long siteId) {
		System.out.println("=======================================================");
		System.out.println("Testing listKeyword API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(REST_SERVICE_URI + "/site/" + siteId + "/keyword");

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request,
				List.class);

		List<LinkedHashMap<String, Object>> keywordsMap = response.getBody();

		if (keywordsMap != null) {
			System.out.println("count: " + keywordsMap.size());
			for (LinkedHashMap<String, Object> map : keywordsMap) {
				System.out.println("Keyword : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", keyword="
						+ map.get("keyword"));
			}
		} else {
			System.out.println("No keyword exist ----------------");
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static void listKeywordPaginated(long siteId, int offset, int size) {
		System.out.println("=======================================================");
		System.out.println("Testing listKeywordPaginated API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(REST_SERVICE_URI + "/site/" + siteId + "/keyword");
		builder.queryParam("offset", offset).queryParam("size", size);
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<List> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET, request,
				List.class);

		List<LinkedHashMap<String, Object>> keywordsMap = response.getBody();

		if (keywordsMap != null) {
			System.out.println("count: " + keywordsMap.size());
			for (LinkedHashMap<String, Object> map : keywordsMap) {
				System.out.println("Keyword : id=" + map.get("id") + ", site_id=" + map.get("siteId") + ", keyword="
						+ map.get("keyword"));
			}
		} else {
			System.out.println("No keyword exist ----------------");
		}
	}

	@SuppressWarnings({ "unchecked" })
	private static void fetchTotalCount(long siteId) {
		System.out.println("=======================================================");
		System.out.println("Testing fetchTotalCount API-----------");

		UriComponentsBuilder builder = UriComponentsBuilder
				.fromHttpUrl(REST_SERVICE_URI + "/site/" + siteId + "/keyword/count");
		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(builder.build().encode().toUri(), HttpMethod.GET,
				request, Object.class);

		LinkedHashMap<String, String> map = (LinkedHashMap<String, String>) response.getBody();
		if (map != null) {
			System.out.println("totalCount : " + map.get("totalCount"));
		} else {
			System.out.println("No keyword exist ----------------");
		}
	}

	private static void createKeyword(long siteId) {
		System.out.println("=======================================================");
		System.out.println("---- Testing createKeyword API----------------- ");

		Keyword keyword = getKeyword();

		System.out.println(keyword);

		HttpEntity<Object> request = new HttpEntity<Object>(keyword, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/keyword",
				HttpMethod.POST, request, Object.class);
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

	private static void updateKeyword(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing updateKeyword API----------------- ");

		Keyword keyword = getKeyword();

		System.out.println(keyword);

		HttpEntity<Object> request = new HttpEntity<Object>(keyword, getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/keyword/" + id,
				HttpMethod.PUT, request, Object.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("Keyword : id =" + map.get("id") + ", site_id =" + map.get("siteId") + ", keyword ="
					+ map.get("keyword") + ", updated_at =" + map.get("updatedAt"));
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static void deleteKeyword(long siteId, long id) {
		System.out.println("=======================================================");
		System.out.println("---- Testing deleteKeyword API----------------- ");

		HttpEntity<String> request = new HttpEntity<String>(getHeaders());
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange(REST_SERVICE_URI + "/site/" + siteId + "/keyword/" + id,
				HttpMethod.DELETE, request, Object.class);
		if (response.getStatusCode() == HttpStatus.NO_CONTENT) {
		} else {
			@SuppressWarnings("unchecked")
			LinkedHashMap<String, Object> map = (LinkedHashMap<String, Object>) response.getBody();
			System.out.println("errorCode=" + map.get("errorCode") + ", message=" + map.get("message"));
		}
	}

	private static Keyword getKeyword() {
		Device device = new Device();
		device.setId(1);
		device.setName("laptop");

		Market market = new Market();
		market.setId(1);
		market.setRegion("region");
		market.setLang("lang");
		market.setRegionName("regionName");
		market.setLangName("langName");

		Keyword keyword = new Keyword();
		keyword.setId(1L);
		keyword.setSiteId(1L);
		keyword.setKeyword("new keyword" + randInt(1, 9));
		keyword.setDevice(device);
		keyword.setMarket(market);

		return keyword;
	}

	public static int randInt(int min, int max) {

		// Usually this can be a field rather than a method variable
		Random rand = new Random();

		// nextInt is normally exclusive of the top value,
		// so add 1 to make it inclusive
		int randomNum = rand.nextInt((max - min) + 1) + min;

		return randomNum;
	}

	public static void main(String[] args) {
		listKeyword(1);
		createKeyword(1);
		createKeyword(1);
		getKeyword(1, 1);
		getKeyword(1, 2);
		updateKeyword(1, 1);
		deleteKeyword(1, 1);
		listKeywordPaginated(1, 0, 5);
		listKeywordPaginated(1, 5, 5);
		fetchTotalCount(1L);
	}
}
