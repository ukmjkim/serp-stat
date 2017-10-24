package com.serpstat.restapi.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

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

import com.serpstat.restapi.exception.SiteStatNotFoundException;
import com.serpstat.restapi.exception.SiteStatNotUniqueInSiteException;
import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.SiteStatService;

public class SiteStatRestControllerTest {
	@Mock
	SiteService siteService;

	@Mock
	SiteStatService siteStatService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	SiteStatRestController siteStatController;

	@Spy
	Site site = new Site();

	@Spy
	List<SiteStat> siteStats = new ArrayList<SiteStat>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		siteStats = getSiteStatList();
		site = getSite();
	}

	@Test
	public void getSiteStat() {
		SiteStat siteStat = siteStats.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		ResponseEntity<SiteStat> response;
		try {
			response = siteStatController.getSiteStat(siteStat.getSiteId(), siteStat.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
		} catch (SiteStatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listSiteStat() {
		SiteStat siteStat = siteStats.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteStatService.findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(siteStats);
		ResponseEntity<List<SiteStat>> response;
		try {
			response = siteStatController.listSiteStat(siteStat.getSiteId(), siteStat.getCrawlDate(), siteStat.getCrawlDate());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteStatService, atLeastOnce()).findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createSiteStatWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(siteStatService).saveSiteStat(any(SiteStat.class));
		SiteStat siteStat = siteStats.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteStatService.isSiteStatUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = siteStatController.createSiteStat(siteStat.getSiteId(), siteStat, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteStatService, atLeastOnce()).isSiteStatUnique(anyLong(), anyLong(), anyInt());
		} catch (SiteNotFoundException e) {

		} catch (SiteStatNotUniqueInSiteException e) {

		}
	}

	@Test
	public void createSiteStatWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(siteStatService).saveSiteStat(any(SiteStat.class));
		SiteStat siteStat = siteStats.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteStatService.isSiteStatUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = siteStatController.createSiteStat(siteStat.getSiteId(), siteStat, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteStatService, atLeastOnce()).isSiteStatUnique(anyLong(), anyLong(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SiteStatNotUniqueInSiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateSiteStatWithNotFound() {
		SiteStat siteStat = siteStats.get(0);
		doNothing().when(siteStatService).updateSiteStat(any(SiteStat.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteStatService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteStat> response;
		try {
			response = siteStatController.updateSiteStat(siteStat.getSiteId(), siteStat);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteStatService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {

		} catch (SiteStatNotFoundException e) {
	
		}
	}

	@Test
	public void updateSiteStatWithSuccess() {
		SiteStat siteStat = siteStats.get(0);
		doNothing().when(siteStatService).updateSiteStat(any(SiteStat.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteStatService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteStat> response;
		try {
			response = siteStatController.updateSiteStat(siteStat.getSiteId(), siteStat);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteStatService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SiteStatNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteSiteStatWithNotFound() {
		SiteStat siteStat = siteStats.get(0);
		doNothing().when(siteStatService).deleteById(anyLong());
		when(siteStatService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteStat> response;
		try {
			response = siteStatController.deleteSiteStat(siteStat.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (SiteStatNotFoundException e) {

		}
	}

	@Test
	public void deleteSiteStatWithSuccess() {
		SiteStat siteStat = siteStats.get(0);
		doNothing().when(siteStatService).deleteById(anyLong());
		when(siteStatService.findById(anyLong())).thenReturn(siteStat);
		ResponseEntity<SiteStat> response;
		try {
			response = siteStatController.deleteSiteStat(siteStat.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (SiteStatNotFoundException e) {
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

	public List<SiteStat> getSiteStatList() {
		SiteStat siteStat1 = new SiteStat();
		siteStat1.setId(1L);
		siteStat1.setSiteId(1L);
		siteStat1.setCrawlDate(20170101);

		SiteStat siteStat2 = new SiteStat();
		siteStat2.setId(2L);
		siteStat2.setSiteId(1L);
		siteStat2.setCrawlDate(20170102);

		siteStats.add(siteStat1);
		siteStats.add(siteStat2);
		return siteStats;
	}
}
