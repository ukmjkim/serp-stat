package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.TagDistribDao;
import com.serpstat.restapi.model.TagDistrib;

public class TagDistribDaoImplTest extends EntityDaoImplTest {
	@Autowired
	TagDistribDao tagDistribDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("TagDistrib.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(tagDistribDao.findById(1));
		Assert.assertNull(tagDistribDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(tagDistribDao.findAllByTagId(1));
		Assert.assertEquals(tagDistribDao.findAllByTagId(10).size(), 0);
	}

	@Test
	public void findBySiteIdAndCrawlDate() {
		Assert.assertNotNull(tagDistribDao.findByTagIdAndCrawlDate(1, 20170101));
		Assert.assertNull(tagDistribDao.findByTagIdAndCrawlDate(1, 20160101));
	}

	@Test
	public void save() {
		int currentCount = tagDistribDao.findAllByTagId(100L).size();

		TagDistrib tagDistrib = new TagDistrib();
		tagDistrib.setTagId(100L);
		tagDistrib.setCrawlDate(20170101);
		tagDistribDao.save(tagDistrib);
		Assert.assertEquals(tagDistribDao.findAllByTagId(100L).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = tagDistribDao.findAllByTagId(1L).size();
		tagDistribDao.deleteById(1);
		Assert.assertEquals(tagDistribDao.findAllByTagId(1L).size(), currentCount - 1);
	}
}
