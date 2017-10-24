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

import com.serpstat.restapi.dao.SiteStatDao;
import com.serpstat.restapi.model.SiteStat;

public class SiteStatServiceImplTest {
	@Mock
	SiteStatDao dao;

	@InjectMocks
	SiteStatServiceImpl siteStatService;

	@Spy
	List<SiteStat> siteStats = new ArrayList<SiteStat>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		siteStats = getSiteStatList();
	}

	@Test
	public void findById() {
		SiteStat siteStat = siteStats.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		Assert.assertEquals(siteStatService.findById(siteStat.getId()), siteStat);
	}

	@Test
	public void findAllBySiteId() {
		when(dao.findAllBySiteId(anyLong())).thenReturn(siteStats);
		Assert.assertEquals(siteStatService.findAllBySiteId(siteStats.get(0).getSiteId()), siteStats);
	}

	@Test
	public void findAllBySiteIdInPeriod() {
		when(dao.findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(siteStats);
		Assert.assertEquals(siteStatService.findAllBySiteIdInPeriod(siteStats.get(0).getSiteId(), 20170101, 20170102), siteStats);
	}

	@Test
	public void saveSiteStat() {
		doNothing().when(dao).save(any(SiteStat.class));
		siteStatService.saveSiteStat((SiteStat) any(SiteStat.class));
		verify(dao, atLeastOnce()).save(any(SiteStat.class));
	}

	@Test
	public void updateSite() {
		SiteStat siteStat = siteStats.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		siteStatService.updateSiteStat(siteStat);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		siteStatService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isSiteTitleUnique() {
		SiteStat siteStat = siteStats.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		Assert.assertEquals(siteStatService.isSiteStatUnique(siteStat.getId(), siteStat.getSiteId(), siteStat.getCrawlDate()), true);
	}

	public List<SiteStat> getSiteStatList() {
		SiteStat siteStat1 = new SiteStat();
		siteStat1.setId(1L);
		siteStat1.setSiteId(1L);
		siteStat1.setCrawlDate(20170101);

		SiteStat siteStat2 = new SiteStat();
		siteStat2.setId(2L);
		siteStat1.setSiteId(1L);
		siteStat1.setCrawlDate(20170102);

		siteStats.add(siteStat1);
		siteStats.add(siteStat2);
		return siteStats;
	}
}
