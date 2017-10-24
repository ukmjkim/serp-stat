package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.SiteSearchVolumeDao;
import com.serpstat.restapi.model.SiteSearchVolume;

public class SiteSearchVolumeDaoImplTest extends EntityDaoImplTest {
	@Autowired
	SiteSearchVolumeDao siteSearchVolumeDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("SiteSearchVolume.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(siteSearchVolumeDao.findById(1));
		Assert.assertNull(siteSearchVolumeDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(siteSearchVolumeDao.findAllBySiteId(1));
		Assert.assertEquals(siteSearchVolumeDao.findAllBySiteId(10).size(), 0);
	}

	@Test
	public void findBySiteIdAndCrawlDate() {
		Assert.assertNotNull(siteSearchVolumeDao.findBySiteIdAndCrawlDate(1, 20170101));
		Assert.assertNull(siteSearchVolumeDao.findBySiteIdAndCrawlDate(1, 20160101));
	}

	@Test
	public void save() {
		int currentCount = siteSearchVolumeDao.findAllBySiteId(100L).size();

		SiteSearchVolume siteSearchVolume = new SiteSearchVolume();
		siteSearchVolume.setSiteId(100L);
		siteSearchVolume.setCrawlDate(20170101);
		siteSearchVolumeDao.save(siteSearchVolume);
		Assert.assertEquals(siteSearchVolumeDao.findAllBySiteId(100L).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = siteSearchVolumeDao.findAllBySiteId(1L).size();
		siteSearchVolumeDao.deleteById(1);
		Assert.assertEquals(siteSearchVolumeDao.findAllBySiteId(1L).size(), currentCount - 1);
	}

}
