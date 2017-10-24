package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.TagStatDao;
import com.serpstat.restapi.model.TagStat;

public class TagStatDaoImplTest extends EntityDaoImplTest {
	@Autowired
	TagStatDao tagStatDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("TagStat.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(tagStatDao.findById(1));
		Assert.assertNull(tagStatDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(tagStatDao.findAllByTagId(1));
		Assert.assertEquals(tagStatDao.findAllByTagId(10).size(), 0);
	}

	@Test
	public void findBySiteIdAndCrawlDate() {
		Assert.assertNotNull(tagStatDao.findByTagIdAndCrawlDate(1, 20170101));
		Assert.assertNull(tagStatDao.findByTagIdAndCrawlDate(1, 20160101));
	}

	@Test
	public void save() {
		int currentCount = tagStatDao.findAllByTagId(100L).size();

		TagStat tagStat = new TagStat();
		tagStat.setTagId(100L);
		tagStat.setCrawlDate(20170101);
		tagStatDao.save(tagStat);
		Assert.assertEquals(tagStatDao.findAllByTagId(100L).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = tagStatDao.findAllByTagId(1L).size();
		tagStatDao.deleteById(1);
		Assert.assertEquals(tagStatDao.findAllByTagId(1L).size(), currentCount - 1);
	}
}
