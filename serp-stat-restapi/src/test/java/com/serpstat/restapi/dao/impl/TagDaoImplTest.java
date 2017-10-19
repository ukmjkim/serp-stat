package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.TagDao;
import com.serpstat.restapi.model.Tag;

public class TagDaoImplTest extends EntityDaoImplTest {
	@Autowired
	TagDao tagDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Tag.xml")) };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(tagDao.findById(1));
		Assert.assertNull(tagDao.findById(1000));
	}

	@Test
	public void findAllBySiteId() {
		Assert.assertNotNull(tagDao.findAllBySiteId(1));
		Assert.assertEquals(tagDao.findAllBySiteId(10).size(), 0);
	}

	@Test
	public void findAllSiteIdAndMatchedTag() {
		Assert.assertEquals(tagDao.findAllSiteIdAndMatchedTag(1, "tag1").size(), 1);
		Assert.assertEquals(tagDao.findAllSiteIdAndMatchedTag(1, "title10").size(), 0);
		Assert.assertEquals(tagDao.findAllSiteIdAndMatchedTag(10, "tag1").size(), 0);
	}

	@Test
	public void findTagBySiteIdAndTag() {
		Assert.assertNotNull(tagDao.findTagBySiteIdAndTag(1, "tag1"));
		Assert.assertNull(tagDao.findTagBySiteIdAndTag(1, "tag10"));
	}

	@Test
	public void save() {
		int currentCount = tagDao.findAllBySiteId(1).size();

		Tag tag = new Tag();
		tag.setSiteId(1L);
		tag.setTag("tag4");
		tagDao.save(tag);
		Assert.assertEquals(tagDao.findAllBySiteId(1).size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = tagDao.findAllBySiteId(1).size();
		tagDao.deleteById(1);
		Assert.assertEquals(tagDao.findAllBySiteId(1).size(), currentCount - 1);
	}
}
