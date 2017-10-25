package com.serpstat.restapi.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.DeviceDao;
import com.serpstat.restapi.model.Device;

public class DeviceDaoImplTest extends EntityPopulatedDataDaoImplTest {
	@Autowired
	DeviceDao deviceDao;

	@Test
	public void findById() {
		Assert.assertNotNull(deviceDao.findById(1));
		Assert.assertNull(deviceDao.findById(1000));
	}

	@Test
	public void findAll() {
		Assert.assertEquals(deviceDao.findAll().size(), 2);
	}

	@Test
	public void save() {
		int currentCount = deviceDao.findAll().size();

		Device device = new Device();
		device.setId(currentCount);
		device.setName("test");
		deviceDao.save(device);
		Assert.assertEquals(deviceDao.findAll().size(), currentCount + 1);
	}

	@Test
	public void deleteById() {
		int currentCount = deviceDao.findAll().size();
		deviceDao.deleteById(1);
		Assert.assertEquals(deviceDao.findAll().size(), currentCount - 1);
	}
}
