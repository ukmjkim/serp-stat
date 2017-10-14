package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.SiteDao;
import com.serpstat.restapi.dao.UserDao;
import com.serpstat.restapi.model.Site;

public class SiteDaoImplTest extends EntityDaoImplTest {
	@Autowired
	SiteDao siteDao;

	@Autowired
	UserDao userDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = {
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("UserAPI.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Site.xml")),
		};
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(siteDao.findById(1));
		Assert.assertNull(siteDao.findById(1000));
	}
	
	@Test
	public void findByUserIdAndTitle() {
		Assert.assertNotNull(siteDao.findByUserIdAndTitle(1, "title1"));
		Assert.assertNull(siteDao.findByUserIdAndTitle(10, "title10"));
	}
	
	@Test
	public void findAllByUserId() {
		Assert.assertNotNull(siteDao.findAllByUserId(1));
		Assert.assertEquals(siteDao.findAllByUserId(10).size(), 0);
	}
	
	@Test
	public void findAll() {
		Assert.assertEquals(siteDao.findAll().size(), 3);
	}

	@Test
	public void save() {
		int currentCount = siteDao.findAll().size();

		Site site = new Site();
		site.setUser(userDao.findById(1));
		site.setTitle("newTitle");
		site.setUrl("www.site.com");
		siteDao.save(site);
		Assert.assertEquals(siteDao.findAll().size(), currentCount+1);
	}
	
	@Test
	public void deleteById() {
		int currentCount = siteDao.findAll().size();
		siteDao.deleteById(1);
		Assert.assertEquals(siteDao.findAll().size(), currentCount-1);
	}
}
