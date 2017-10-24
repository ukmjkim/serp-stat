package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.SiteStatDao;
import com.serpstat.restapi.model.SiteStat;

public class SiteStatDaoImplTest extends EntityDaoImplTest {
	@Autowired
	SiteStatDao siteStatDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("SiteStat.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(siteStatDao.findById(1));
		Assert.assertNull(siteStatDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(siteStatDao.findAllBySiteId(1));
		Assert.assertEquals(siteStatDao.findAllBySiteId(10).size(), 0);
	}

	@Test
	public void findBySiteIdAndCrawlDate() {
		Assert.assertNotNull(siteStatDao.findBySiteIdAndCrawlDate(1, 20170101));
		Assert.assertNull(siteStatDao.findBySiteIdAndCrawlDate(1, 20160101));
	}

	@Test
	public void save() {
		int currentCount = siteStatDao.findAllBySiteId(100L).size();

		SiteStat siteStat = new SiteStat();
		siteStat.setSiteId(100L);
		siteStat.setCrawlDate(20170101);
		siteStatDao.save(siteStat);
		Assert.assertEquals(siteStatDao.findAllBySiteId(100L).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = siteStatDao.findAllBySiteId(1L).size();
		siteStatDao.deleteById(1);
		Assert.assertEquals(siteStatDao.findAllBySiteId(1L).size(), currentCount - 1);
	}
}
