package com.serpstat.restapi.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.exception.KeywordNotFoundException;
import com.serpstat.restapi.exception.KeywordNotUniqueInSiteException;
import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.model.Device;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.model.Market;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.service.KeywordService;
import com.serpstat.restapi.service.SiteService;

public class KeywordRestControllerTest {
	@Mock
	SiteService siteService;

	@Mock
	KeywordService keywordService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	KeywordRestController keywordController;

	@Spy
	Site site = new Site();

	@Spy
	List<Keyword> keywords = new ArrayList<Keyword>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		keywords = getKeywordList();
		site = getSite();
	}

	@Test
	public void getKeyword() {
		Keyword keyword = keywords.get(0);
		when(keywordService.findById(anyLong())).thenReturn(keyword);
		ResponseEntity<Keyword> response;
		try {
			response = keywordController.getKeyword(keyword.getSiteId(), keyword.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(keywordService, atLeastOnce()).findById(anyLong());
		} catch (KeywordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listKeyword() {
		Keyword keyword = keywords.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.findAllBySiteId(anyLong())).thenReturn(keywords);
		ResponseEntity<List<Keyword>> response;
		try {
			response = keywordController.listKeyword(keyword.getSiteId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).findAllBySiteId(anyLong());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listKeywordPaginated() {
		Keyword keyword = keywords.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.findPagenatedBySiteId(anyLong(), anyInt(), anyInt())).thenReturn(keywords);
		ResponseEntity<List<Keyword>> response;
		try {
			response = keywordController.listKeywordPaginated(keyword.getSiteId(), 0, 500);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).findPagenatedBySiteId(anyLong(), anyInt(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void fetchTotalCount() {
		Keyword keyword = keywords.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.findTotalCountBySiteId(anyLong())).thenReturn(1);
		ResponseEntity<Map<String, Integer>> response;
		try {
			response = keywordController.fetchTotalCount(keyword.getSiteId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).findTotalCountBySiteId(anyLong());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createKeywordWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(keywordService).saveKeyword(any(Keyword.class));
		Keyword keyword = keywords.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.isKeywordInSiteUnique(anyLong(), anyString())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = keywordController.createKeyword(keyword.getSiteId(), keyword, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).isKeywordInSiteUnique(anyLong(), anyString());
		} catch (SiteNotFoundException e) {

		} catch (KeywordNotUniqueInSiteException e) {

		}
	}

	@Test
	public void createKeywordWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(keywordService).saveKeyword(any(Keyword.class));
		Keyword keyword = keywords.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.isKeywordInSiteUnique(anyLong(), anyString())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = keywordController.createKeyword(keyword.getSiteId(), keyword, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).isKeywordInSiteUnique(anyLong(), anyString());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeywordNotUniqueInSiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateKeywordWithNotFound() {
		Keyword keyword = keywords.get(0);
		doNothing().when(keywordService).updateKeyword(any(Keyword.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Keyword> response;
		try {
			response = keywordController.updateKeyword(keyword.getSiteId(), keyword.getId(), keyword);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {

		} catch (KeywordNotFoundException e) {

		}
	}

	@Test
	public void updateKeywordWithSuccess() {
		Keyword keyword = keywords.get(0);
		doNothing().when(keywordService).updateKeyword(any(Keyword.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(keywordService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Keyword> response;
		try {
			response = keywordController.updateKeyword(keyword.getSiteId(), keyword.getId(), keyword);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(keywordService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (KeywordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteKeywordWithNotFound() {
		Keyword keyword = keywords.get(0);
		doNothing().when(keywordService).deleteById(anyLong());
		when(keywordService.findById(anyLong())).thenReturn(null);
		ResponseEntity<Keyword> response;
		try {
			response = keywordController.deleteKeyword(keyword.getSiteId(), keyword.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (KeywordNotFoundException e) {

		}
	}

	@Test
	public void deleteKeywordWithSuccess() {
		Keyword keyword = keywords.get(0);
		doNothing().when(keywordService).deleteById(anyLong());
		when(keywordService.findById(anyLong())).thenReturn(keyword);
		ResponseEntity<Keyword> response;
		try {
			response = keywordController.deleteKeyword(keyword.getSiteId(), keyword.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (KeywordNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Site getSite() {
		User user = new User();
		user.setLogin("newLogin");
		user.setPassword("password");
		user.setNiceName("nicename");
		user.setEmail("email");

		Site site = new Site();
		site.setId(1L);
		site.setUser(user);
		site.setTitle("newTitle1");
		site.setUrl("www.url1.com");

		return site;
	}

	public List<Keyword> getKeywordList() {
		List<Keyword> keywords = new ArrayList<Keyword>();

		Device device = new Device();
		device.setId(1);
		device.setName("laptop");

		Market market = new Market();
		market.setId(1);
		market.setRegion("region");
		market.setLang("lang");
		market.setRegionName("regionName");
		market.setLangName("langName");

		Keyword keyword1 = new Keyword();
		keyword1.setId(1001L);
		keyword1.setSiteId(1L);
		keyword1.setKeyword("new keyword1");
		keyword1.setDevice(device);
		keyword1.setMarket(market);

		Keyword keyword2 = new Keyword();
		keyword2.setId(1002L);
		keyword2.setSiteId(1L);
		keyword2.setKeyword("new keyword2");
		keyword2.setDevice(device);
		keyword2.setMarket(market);

		keywords.add(keyword1);
		keywords.add(keyword2);

		return keywords;
	}
}
