package com.serpstat.restapi.controller;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.TagService;

public class SiteRestControllerTest {
	@Mock
	SiteService siteService;

	@Mock
	TagService tagService;

	@InjectMocks
	SiteRestController siteController;

	@Spy
	UriComponentsBuilder ucBuilder;

	@Spy
	List<Site> sites = new ArrayList<Site>();

	@Spy
	List<Tag> tags = new ArrayList<Tag>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sites = getSiteList();
		tags = getTagList();
	}

	@Test
	public void listAllSites() {
		when(siteService.findAll()).thenReturn(sites);
		ResponseEntity<List<Site>> response = siteController.listAllSites();
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		verify(siteService, atLeastOnce()).findAll();
	}

	@Test
	public void getSite() {
		Site site = sites.get(0);
		when(siteService.findById(anyLong())).thenReturn(site);
		ResponseEntity<Site> response;
		try {
			response = siteController.getSite(site.getId());
			Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		} catch (SiteNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		verify(siteService, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void listAllTagsBySiteId() {
		when(tagService.findAllBySiteId(anyLong())).thenReturn(tags);
		ResponseEntity<List<Tag>> response = siteController.listAllTagsBySiteId(1L);
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		verify(tagService, atLeastOnce()).findAllBySiteId(anyLong());
	}

	public List<Site> getSiteList() {
		User user = new User();
		user.setLogin("newLogin");
		user.setPassword("password");
		user.setNiceName("nicename");
		user.setEmail("email");

		List<Site> sites = new ArrayList<Site>();

		Site site1 = new Site();
		site1.setId(1L);
		site1.setUser(user);
		site1.setTitle("newTitle1");
		site1.setUrl("www.url1.com");

		Site site2 = new Site();
		site2.setId(2L);
		site2.setUser(user);
		site2.setTitle("newTitle2");
		site2.setUrl("www.url2.com");

		sites.add(site1);
		sites.add(site2);
		return sites;
	}

	public List<Tag> getTagList() {
		Tag tag1 = new Tag();
		tag1.setId(1L);
		tag1.setSiteId(1L);
		tag1.setTag("tag1");

		Tag tag2 = new Tag();
		tag2.setId(2L);
		tag2.setSiteId(1L);
		tag2.setTag("tag2");

		tags.add(tag1);
		tags.add(tag2);
		return tags;
	}
}
