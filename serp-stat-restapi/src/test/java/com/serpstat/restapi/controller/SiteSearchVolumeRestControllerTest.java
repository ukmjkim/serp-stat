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

import com.serpstat.restapi.exception.SiteSearchVolumeNotFoundException;
import com.serpstat.restapi.exception.SiteSearchVolumeNotUniqueInSiteException;
import com.serpstat.restapi.exception.SiteStatNotFoundException;
import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteSearchVolume;
import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.SiteSearchVolumeService;

public class SiteSearchVolumeRestControllerTest {
	@Mock
	SiteService siteService;

	@Mock
	SiteSearchVolumeService siteSearchVolumeService;

	@Mock
	HttpHeaders headers;

	@InjectMocks
	SiteSearchVolumeRestController siteSearchVolumeController;

	@Spy
	Site site = new Site();

	@Spy
	List<SiteSearchVolume> siteSearchVolumes = new ArrayList<SiteSearchVolume>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		siteSearchVolumes = getSiteSearchVolumeList();
		site = getSite();
	}

	@Test
	public void getSiteStat() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		ResponseEntity<SiteSearchVolume> response;
		try {
			response = siteSearchVolumeController.getSiteSearchVolume(siteSearchVolume.getSiteId(), siteSearchVolume.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
		} catch (SiteSearchVolumeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void listSiteSearchVolume() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteSearchVolumeService.findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(siteSearchVolumes);
		ResponseEntity<List<SiteSearchVolume>> response;
		try {
			response = siteSearchVolumeController.listSiteSearchVolume(siteSearchVolume.getSiteId(), siteSearchVolume.getCrawlDate(), siteSearchVolume.getCrawlDate());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteSearchVolumeService, atLeastOnce()).findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void createSiteSearchVolumeWithConflict() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(siteSearchVolumeService).saveSiteSearchVolume(any(SiteSearchVolume.class));
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteSearchVolumeService.isSiteSearchVolumeUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = siteSearchVolumeController.createSiteSearchVolume(siteSearchVolume.getSiteId(), siteSearchVolume, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteSearchVolumeService, atLeastOnce()).isSiteSearchVolumeUnique(anyLong(), anyLong(), anyInt());
		} catch (SiteNotFoundException e) {

		} catch (SiteSearchVolumeNotUniqueInSiteException e) {

		}
	}

	@Test
	public void createSiteSearchVolumeWithSuccess() {
		UriComponentsBuilder ucBuilder = UriComponentsBuilder.newInstance();
		doNothing().when(siteSearchVolumeService).saveSiteSearchVolume(any(SiteSearchVolume.class));
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteSearchVolumeService.isSiteSearchVolumeUnique(anyLong(), anyLong(), anyInt())).thenReturn(false);
		ResponseEntity<Void> response;
		try {
			response = siteSearchVolumeController.createSiteSearchVolume(siteSearchVolume.getSiteId(), siteSearchVolume, ucBuilder);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteSearchVolumeService, atLeastOnce()).isSiteSearchVolumeUnique(anyLong(), anyLong(), anyInt());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SiteSearchVolumeNotUniqueInSiteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void updateSiteSearchVolumeWithNotFound() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		doNothing().when(siteSearchVolumeService).updateSiteSearchVolume(any(SiteSearchVolume.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteSearchVolumeService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteSearchVolume> response;
		try {
			response = siteSearchVolumeController.updateSiteSearchVolume(siteSearchVolume.getSiteId(), siteSearchVolume);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteSearchVolumeService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {

		} catch (SiteSearchVolumeNotFoundException e) {
	
		}
	}

	@Test
	public void updateSiteSearchVolumeWithSuccess() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		doNothing().when(siteSearchVolumeService).updateSiteSearchVolume(any(SiteSearchVolume.class));
		when(siteService.findById(anyLong())).thenReturn(site);
		when(siteSearchVolumeService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteSearchVolume> response;
		try {
			response = siteSearchVolumeController.updateSiteSearchVolume(siteSearchVolume.getSiteId(), siteSearchVolume);
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
			verify(siteService, atLeastOnce()).findById(anyLong());
			verify(siteSearchVolumeService, atLeastOnce()).findById(anyLong());
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SiteSearchVolumeNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void deleteSiteSearchVolumeWithNotFound() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		doNothing().when(siteSearchVolumeService).deleteById(anyLong());
		when(siteSearchVolumeService.findById(anyLong())).thenReturn(null);
		ResponseEntity<SiteSearchVolume> response;
		try {
			response = siteSearchVolumeController.deleteSiteSearchVolume(siteSearchVolume.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		} catch (SiteSearchVolumeNotFoundException e) {

		}
	}

	@Test
	public void deleteSiteSearchVolumeWithSuccess() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		doNothing().when(siteSearchVolumeService).deleteById(anyLong());
		when(siteSearchVolumeService.findById(anyLong())).thenReturn(siteSearchVolume);
		ResponseEntity<SiteSearchVolume> response;
		try {
			response = siteSearchVolumeController.deleteSiteSearchVolume(siteSearchVolume.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
		} catch (SiteSearchVolumeNotFoundException e) {
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

	public List<SiteSearchVolume> getSiteSearchVolumeList() {
		SiteSearchVolume siteSearchVolume1 = new SiteSearchVolume();
		siteSearchVolume1.setId(1L);
		siteSearchVolume1.setSiteId(1L);
		siteSearchVolume1.setCrawlDate(20170101);

		SiteSearchVolume siteSearchVolume2 = new SiteSearchVolume();
		siteSearchVolume2.setId(2L);
		siteSearchVolume2.setSiteId(1L);
		siteSearchVolume2.setCrawlDate(20170102);

		siteSearchVolumes.add(siteSearchVolume1);
		siteSearchVolumes.add(siteSearchVolume2);
		return siteSearchVolumes;
	}
}
