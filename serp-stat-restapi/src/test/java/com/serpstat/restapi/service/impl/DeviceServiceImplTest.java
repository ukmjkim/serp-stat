package com.serpstat.restapi.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.serpstat.restapi.dao.DeviceDao;
import com.serpstat.restapi.model.Device;

public class DeviceServiceImplTest {
	@Mock
	DeviceDao dao;

	@InjectMocks
	DeviceServiceImpl deviceService;

	@Spy
	List<Device> devices = new ArrayList<Device>();

	@BeforeClass
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		devices = getDeviceList();
	}

	@Test
	public void findById() {
		Device device = devices.get(0);
		when(dao.findById(anyInt())).thenReturn(device);
		Assert.assertEquals(deviceService.findById(device.getId()), device);
	}

	@Test
	public void findAll() {
		when(dao.findAll()).thenReturn(devices);
		Assert.assertEquals(deviceService.findAll(), devices);
	}

	@Test
	public void saveDevice() {
		doNothing().when(dao).save(any(Device.class));
		deviceService.saveDevice((Device) any(Device.class));
		verify(dao, atLeastOnce()).save(any(Device.class));
	}

	@Test
	public void updateSite() {
		Device device = devices.get(0);
		when(dao.findById(anyInt())).thenReturn(device);
		deviceService.updateDevice(device);
		verify(dao, atLeastOnce()).findById(anyInt());
	}

	@Test
	public void deleteById() {
		doNothing().when(dao).deleteById(anyInt());
		deviceService.deleteById(anyInt());
		verify(dao, atLeastOnce()).deleteById(anyInt());
	}

	@Test
	public void isDeviceUnique() {
		Device device = devices.get(0);
		when(dao.findByName(anyString())).thenReturn(device);
		Assert.assertEquals(deviceService.isDeviceUnique(device.getName()), true);
	}

	public List<Device> getDeviceList() {
		List<Device> devices = new LinkedList<>();
		Device device1 = new Device();
		device1.setId(1);
		device1.setName("desktop");

		Device device2 = new Device();
		device2.setId(1);
		device2.setName("desktop");

		devices.add(device1);
		devices.add(device1);

		return devices;
	}
}
