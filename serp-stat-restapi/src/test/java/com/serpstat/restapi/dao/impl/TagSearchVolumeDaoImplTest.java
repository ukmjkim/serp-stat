package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.TagSearchVolumeDao;
import com.serpstat.restapi.model.TagSearchVolume;

public class TagSearchVolumeDaoImplTest extends EntityDaoImplTest {
	@Autowired
	TagSearchVolumeDao tagSearchVolumeDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("TagSearchVolume.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(tagSearchVolumeDao.findById(1));
		Assert.assertNull(tagSearchVolumeDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(tagSearchVolumeDao.findAllByTagId(1));
		Assert.assertEquals(tagSearchVolumeDao.findAllByTagId(10).size(), 0);
	}

	@Test
	public void findBySiteIdAndCrawlDate() {
		Assert.assertNotNull(tagSearchVolumeDao.findByTagIdAndCrawlDate(1, 20170101));
		Assert.assertNull(tagSearchVolumeDao.findByTagIdAndCrawlDate(1, 20160101));
	}

	@Test
	public void save() {
		int currentCount = tagSearchVolumeDao.findAllByTagId(100L).size();

		TagSearchVolume tagSearchVolume = new TagSearchVolume();
		tagSearchVolume.setTagId(100L);
		tagSearchVolume.setCrawlDate(20170101);
		tagSearchVolumeDao.save(tagSearchVolume);
		Assert.assertEquals(tagSearchVolumeDao.findAllByTagId(100L).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = tagSearchVolumeDao.findAllByTagId(1L).size();
		tagSearchVolumeDao.deleteById(1);
		Assert.assertEquals(tagSearchVolumeDao.findAllByTagId(1L).size(), currentCount - 1);
	}
}
