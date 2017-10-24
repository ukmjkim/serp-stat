package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.SiteDistribDao;
import com.serpstat.restapi.model.SiteDistrib;

public class SiteDistribDaoImplTest extends EntityDaoImplTest {
	@Autowired
	SiteDistribDao siteDistribDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("SiteDistrib.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(siteDistribDao.findById(1));
		Assert.assertNull(siteDistribDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(siteDistribDao.findAllBySiteId(1));
		Assert.assertEquals(siteDistribDao.findAllBySiteId(10).size(), 0);
	}

	@Test
	public void findBySiteIdAndCrawlDate() {
		Assert.assertNotNull(siteDistribDao.findBySiteIdAndCrawlDate(1, 20170101));
		Assert.assertNull(siteDistribDao.findBySiteIdAndCrawlDate(1, 20160101));
	}

	@Test
	public void save() {
		int currentCount = siteDistribDao.findAllBySiteId(100L).size();

		SiteDistrib siteDistrib = new SiteDistrib();
		siteDistrib.setSiteId(100L);
		siteDistrib.setCrawlDate(20170101);
		siteDistribDao.save(siteDistrib);
		Assert.assertEquals(siteDistribDao.findAllBySiteId(100L).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = siteDistribDao.findAllBySiteId(1L).size();
		siteDistribDao.deleteById(1);
		Assert.assertEquals(siteDistribDao.findAllBySiteId(1L).size(), currentCount - 1);
	}

}
