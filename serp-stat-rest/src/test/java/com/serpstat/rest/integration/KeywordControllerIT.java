package com.serpstat.rest.integration;

import static com.serpstat.rest.utils.JsonConverter.convertJsonToList;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Before;
import org.json.JSONException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.serpstat.rest.RestApplication;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RestApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class KeywordControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	@Before(value = "")
	public void before() {
		//headers.add("Authorization", createHttpAuthenticationHeaderValue("user1", "secret1"));
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));

	}

//	private String createHttpAuthenticationHeaderValue(String userId, String password) {
//
//		String auth = userId + ":" + password;
//
//		byte[] encodedAuth = Base64.encode(auth.getBytes(Charset.forName("US-ASCII")));
//
//		String headerValue = "Basic " + new String(encodedAuth);
//
//		return headerValue;
//	}

	@Test
	public void testlistAllKeywords() throws JSONException {

		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange(
				createURLWithPort("/api/v1/site/1/keywords"), HttpMethod.GET, entity, String.class);

		List<Map<String, Object>> list = convertJsonToList(response.getBody());
		assertEquals(true, (list.size() > 0));
	}

	private String createURLWithPort(final String uri) {
		return "http://localhost:" + port + uri;
	}
}
