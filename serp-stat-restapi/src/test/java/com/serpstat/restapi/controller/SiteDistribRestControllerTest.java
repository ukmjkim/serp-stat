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

import com.serpstat.restapi.exception.SiteDistribNotFoundException;
import com.serpstat.restapi.exception.SiteDistribNotUniqueInSiteException;
import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.exception.SiteSearchVolumeNotFoundException;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteDistrib;
import com.serpstat.restapi.model.SiteSearchVolume;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.SiteDistribService;

public class SiteDistribRestControllerTest {
	@Mock
	SiteService siteService;

	@Mock
	SiteDistribService siteDistribService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	SiteDistribRestController siteDistribController;

	@Spy
	Site site = new Site();

	@Spy
	List<SiteDistrib> siteDistribs = new ArrayList<SiteDistrib>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		siteDistribs = getSiteDistribList();
		site = getSite();
	}

	@Test
	public void getSiteDistrib() {
		SiteDistrib siteDistrib = siteDistribs.get(0);
		when(siteDistribService.findById(anyLong())).thenReturn(siteDistrib);
		ResponseEntity<SiteDistrib> response;
		try {
			response = siteDistribController.getSiteDistrib(siteDistrib.getSiteId(), siteDistrib.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteDistribService, atLeastOnce()).findById(anyLong());
		} catch (SiteDistribNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listSiteDistrib() {
		SiteDistrib siteDistrib = siteDistribs.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteDistribService.findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(siteDistribs);
		ResponseEntity<List<SiteDistrib>> response;
		try {
			response = siteDistribController.listSiteDistrib(siteDistrib.getSiteId(), siteDistrib.getCrawlDate(), siteDistrib.getCrawlDate());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteDistribService, atLeastOnce()).findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createSiteDistribWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(siteDistribService).saveSiteDistrib(any(SiteDistrib.class));
		SiteDistrib siteDistrib = siteDistribs.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteDistribService.isSiteDistribUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = siteDistribController.createSiteDistrib(siteDistrib.getSiteId(), siteDistrib, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteDistribService, atLeastOnce()).isSiteDistribUnique(anyLong(), anyLong(), anyInt());
		} catch (SiteNotFoundException e) {

		} catch (SiteDistribNotUniqueInSiteException e) {

		}
	}

	@Test
	public void createSiteDistribWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(siteDistribService).saveSiteDistrib(any(SiteDistrib.class));
		SiteDistrib siteDistrib = siteDistribs.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteDistribService.isSiteDistribUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = siteDistribController.createSiteDistrib(siteDistrib.getSiteId(), siteDistrib, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteDistribService, atLeastOnce()).isSiteDistribUnique(anyLong(), anyLong(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SiteDistribNotUniqueInSiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateSiteDistribWithNotFound() {
		SiteDistrib siteDistrib = siteDistribs.get(0);
		doNothing().when(siteDistribService).updateSiteDistrib(any(SiteDistrib.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteDistribService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteDistrib> response;
		try {
			response = siteDistribController.updateSiteDistrib(siteDistrib.getSiteId(), siteDistrib);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteDistribService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {

		} catch (SiteDistribNotFoundException e) {
	
		}
	}

	@Test
	public void updateSiteDistribWithSuccess() {
		SiteDistrib siteDistrib = siteDistribs.get(0);
		doNothing().when(siteDistribService).updateSiteDistrib(any(SiteDistrib.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteDistribService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteDistrib> response;
		try {
			response = siteDistribController.updateSiteDistrib(siteDistrib.getSiteId(), siteDistrib);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteDistribService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SiteDistribNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteSiteDistribWithNotFound() {
		SiteDistrib siteDistrib = siteDistribs.get(0);
		doNothing().when(siteDistribService).deleteById(anyLong());
		when(siteDistribService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteDistrib> response;
		try {
			response = siteDistribController.deleteSiteDistrib(siteDistrib.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (SiteDistribNotFoundException e) {

		}
	}

	@Test
	public void deleteSiteDistribWithSuccess() {
		SiteDistrib siteDistrib = siteDistribs.get(0);
		doNothing().when(siteDistribService).deleteById(anyLong());
		when(siteDistribService.findById(anyLong())).thenReturn(siteDistrib);
		ResponseEntity<SiteDistrib> response;
		try {
			response = siteDistribController.deleteSiteDistrib(siteDistrib.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (SiteDistribNotFoundException e) {
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

	public List<SiteDistrib> getSiteDistribList() {
		SiteDistrib siteDistrib1 = new SiteDistrib();
		siteDistrib1.setId(1L);
		siteDistrib1.setSiteId(1L);
		siteDistrib1.setCrawlDate(20170101);

		SiteDistrib siteDistrib2 = new SiteDistrib();
		siteDistrib2.setId(2L);
		siteDistrib2.setSiteId(1L);
		siteDistrib2.setCrawlDate(20170102);

		siteDistribs.add(siteDistrib1);
		siteDistribs.add(siteDistrib2);
		return siteDistribs;
	}
}
