package com.serpstat.restapi.service.impl;

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

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.SiteDao;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.User;

public class SiteServiceImplTest {
	@Mock
	SiteDao dao;

	@InjectMocks
	SiteServiceImpl siteService;

	@Spy
	User user = null;

	@Spy
	List<Site> sites = new ArrayList<Site>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		sites = getSiteList();
	}

	@Test
	public void findById() {
		Site site = sites.get(0);
		when(dao.findById(anyLong())).thenReturn(site);
		Assert.assertEquals(siteService.findById(site.getId()), site);
	}

	@Test
	public void findAllByUserId() {
		when(dao.findAllByUserId(anyLong())).thenReturn(sites);
		Assert.assertEquals(siteService.findAllByUserId(sites.get(0).getId()), sites);
	}

	@Test
	public void findAll() {
		when(dao.findAll()).thenReturn(sites);
		Assert.assertEquals(siteService.findAll(), sites);
	}

	@Test
	public void saveSite() {
		doNothing().when(dao).save(any(Site.class));
		siteService.saveSite((Site) any(Site.class));
		verify(dao, atLeastOnce()).save(any(Site.class));
	}

	@Test
	public void updateSite() {
		Site site = sites.get(0);
		when(dao.findById(anyLong())).thenReturn(site);
		siteService.updateSite(site);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		siteService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isSiteTitleUnique() {
		Site site = sites.get(0);
		when(dao.findById(anyLong())).thenReturn(site);
		Assert.assertEquals(siteService.isSiteTitleUnique(site.getId(), user.getId(), site.getTitle()), true);
	}

	public List<Site> getSiteList() {
		user = new User();
		user.setId(1L);
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
}
