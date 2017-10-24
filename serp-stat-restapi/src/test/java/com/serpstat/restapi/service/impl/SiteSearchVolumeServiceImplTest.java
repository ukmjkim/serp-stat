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

import com.serpstat.restapi.dao.SiteSearchVolumeDao;
import com.serpstat.restapi.model.SiteSearchVolume;

public class SiteSearchVolumeServiceImplTest {
	@Mock
	SiteSearchVolumeDao dao;

	@InjectMocks
	SiteSearchVolumeServiceImpl siteSearchVolumeService;

	@Spy
	List<SiteSearchVolume> siteSearchVolumes = new ArrayList<SiteSearchVolume>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		siteSearchVolumes = getSiteSearchVolumeList();
	}

	@Test
	public void findById() {
		SiteSearchVolume siteStat = siteSearchVolumes.get(0);
		when(dao.findById(anyLong())).thenReturn(siteStat);
		Assert.assertEquals(siteSearchVolumeService.findById(siteStat.getId()), siteStat);
	}

	@Test
	public void findAllBySiteId() {
		when(dao.findAllBySiteId(anyLong())).thenReturn(siteSearchVolumes);
		Assert.assertEquals(siteSearchVolumeService.findAllBySiteId(siteSearchVolumes.get(0).getSiteId()), siteSearchVolumes);
	}

	@Test
	public void findAllBySiteIdInPeriod() {
		when(dao.findAllBySiteIdInPeriod(anyLong(), anyInt(), anyInt())).thenReturn(siteSearchVolumes);
		Assert.assertEquals(siteSearchVolumeService.findAllBySiteIdInPeriod(siteSearchVolumes.get(0).getSiteId(), 20170101, 20170102), siteSearchVolumes);
	}

	@Test
	public void saveSiteStat() {
		doNothing().when(dao).save(any(SiteSearchVolume.class));
		siteSearchVolumeService.saveSiteSearchVolume((SiteSearchVolume) any(SiteSearchVolume.class));
		verify(dao, atLeastOnce()).save(any(SiteSearchVolume.class));
	}

	@Test
	public void updateSite() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		when(dao.findById(anyLong())).thenReturn(siteSearchVolume);
		siteSearchVolumeService.updateSiteSearchVolume(siteSearchVolume);
		verify(dao, atLeastOnce()).findById(anyLong());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyLong());
		siteSearchVolumeService.deleteById(anyLong());
		verify(dao, atLeastOnce()).deleteById(anyLong());
	}

	@Test
	public void isSiteTitleUnique() {
		SiteSearchVolume siteSearchVolume = siteSearchVolumes.get(0);
		when(dao.findById(anyLong())).thenReturn(siteSearchVolume);
		Assert.assertEquals(siteSearchVolumeService.isSiteSearchVolumeUnique(siteSearchVolume.getId(), siteSearchVolume.getSiteId(), siteSearchVolume.getCrawlDate()), true);
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
