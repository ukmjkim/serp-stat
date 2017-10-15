package com.serpstat.restapi.dao.impl;

import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.UserDao;
import com.serpstat.restapi.model.User;

public class UserDaoImplTest extends EntityDaoImplTest {
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
		Assert.assertNotNull(userDao.findById(1));
		Assert.assertNull(userDao.findById(1000));
	}
	
	@Test
	public void findByLogin() {
		Assert.assertNotNull(userDao.findByLogin("testuser1"));
		Assert.assertNull(userDao.findByLogin("dataNotFound"));
	}
	
	@Test
	public void findAllUsers() {
		Assert.assertEquals(userDao.findAllUsers().size(), 3);
	}

	@Test
	public void save() {
		int currentCount = userDao.findAllUsers().size();
		User user = new User();
		user.setLogin("new");
		user.setPassword("password");
		user.setNiceName("nicename");
		user.setEmail("email");
		userDao.save(user);
		Assert.assertEquals(userDao.findAllUsers().size(), currentCount+1);
	}
	
//	@Test
//	public void deleteByLogin() {
//		int currentCount = userDao.findAllUsers().size();
//		userDao.deleteByLogin("testuser1");
//		Assert.assertEquals(userDao.findAllUsers().size(), currentCount-1);
//	}
}
