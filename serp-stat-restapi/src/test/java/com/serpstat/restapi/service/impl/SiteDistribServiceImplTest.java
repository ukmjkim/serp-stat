package com.serpstat.restapi.service.impl;

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
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.SiteDistribDao;
import com.serpstat.restapi.model.SiteDistrib;

public class SiteDistribServiceImplTest {
	@Mock
	SiteDistribDao dao;

	@InjectMocks
	SiteDistribServiceImpl siteDistribService;

	@Spy
	List<SiteDistrib> siteDistribs = new ArrayList<SiteDistrib>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		siteDistribs = getSiteDistribList();
	}

	@Test
	public void findById() {
		SiteDistrib siteStat = siteDistribs.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		Assert.assertEquals(siteDistribService.findById(siteStat.getId()), siteStat);
	}

	@Test
	public void findAllBySiteId() {
		when(dao.findAllBySiteId(anyLong())).thenReturn(siteDistribs);
		Assert.assertEquals(siteDistribService.findAllBySiteId(siteDistribs.get(0).getSiteId()), siteDistribs);
	}

	@Test
	public void findAllBySiteIdInPeriod() {
		when(dao.findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(siteDistribs);
		Assert.assertEquals(siteDistribService.findAllBySiteIdInPeriod(siteDistribs.get(0).getSiteId(), 20170101, 20170102), siteDistribs);
	}

	@Test
	public void saveSiteStat() {
		doNothing().when(dao).save(any(SiteDistrib.class));
		siteDistribService.saveSiteDistrib((SiteDistrib) any(SiteDistrib.class));
		verify(dao, atLeastOnce()).save(any(SiteDistrib.class));
	}

	@Test
	public void updateSite() {
		SiteDistrib siteStat = siteDistribs.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		siteDistribService.updateSiteDistrib(siteStat);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		siteDistribService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isSiteTitleUnique() {
		SiteDistrib siteStat = siteDistribs.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		Assert.assertEquals(siteDistribService.isSiteDistribUnique(siteStat.getId(), siteStat.getSiteId(), siteStat.getCrawlDate()), true);
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

