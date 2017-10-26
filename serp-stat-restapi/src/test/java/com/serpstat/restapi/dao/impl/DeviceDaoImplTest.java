package com.serpstat.restapi.dao.impl;

import java.util.List;

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
	public void findByName() {
		Assert.assertEquals(deviceDao.findByName("desktop").getName(), "desktop");
	}

	@Test
	public void save() {
		List<Device> devices = deviceDao.findAll();
		int currentCount = devices.size();

		int max = 1;
		for (Device device : devices) {
			if (max < device.getId()) {
				max = device.getId();
			}
		}

		Device device = new Device();
		device.setId(max + 1);
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
