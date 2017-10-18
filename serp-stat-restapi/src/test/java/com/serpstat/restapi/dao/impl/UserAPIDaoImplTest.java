package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.UserAPIDao;
import com.serpstat.restapi.dao.UserDao;
import com.serpstat.restapi.model.UserAPI;

public class UserAPIDaoImplTest extends EntityDaoImplTest {
	@Autowired
	UserAPIDao userApiDao;

	@Autowired
	UserDao userDao;

	@Override
	protected IDataSet getDataSet() throws Exception {
		IDataSet dataSets[] = { new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("User.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("UserAPI.xml")),
				new FlatXmlDataSet(this.getClass().getClassLoader().getResourceAsStream("Site.xml")), };
		return new CompositeDataSet(dataSets);
	}

	@Test
	public void findById() {
		Assert.assertNotNull(userApiDao.findById(1));
		Assert.assertNull(userApiDao.findById(1000));
	}

	@Test
	public void findByKey() {
		Assert.assertNotNull(userApiDao.findByKey("key1"));
		Assert.assertNull(userApiDao.findByKey("key10"));
	}

	@Test
	public void findAllByUserId() {
		Assert.assertNotNull(userApiDao.findAllByUserId(1));
		Assert.assertEquals(userApiDao.findAllByUserId(1000L).size(), 0);
	}

	@Test
	public void findAll() {
		Assert.assertEquals(userApiDao.findAll().size(), 3);
	}

	@Test
	public void save() {
		int currentCount = userApiDao.findAll().size();

		UserAPI userApi = new UserAPI();
		userApi.setUser(userDao.findById(1));
		userApi.setApiKey("newKey");
		userApi.setIps("");
		userApiDao.save(userApi);
		Assert.assertEquals(userApiDao.findAll().size(), currentCount + 1);
	}

	@Test
	public void deleteByKey() {
		int currentCount = userApiDao.findAll().size();
		userApiDao.deleteByKey(userApiDao.findById(1).getApiKey());
		Assert.assertEquals(userApiDao.findAll().size(), currentCount - 1);
	}
}
