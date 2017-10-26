package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.DeviceDao;
import com.serpstat.restapi.model.Device;
import com.serpstat.restapi.service.DeviceService;

@Service("deviceService")
@Transactional
public class DeviceServiceImpl implements DeviceService {
	@Autowired
	DeviceDao dao;

	public Device findById(int id) {
		return dao.findById(id);
	}

	public List<Device> findAll() {
		return dao.findAll();
	}

	public void saveDevice(Device device) {
		dao.save(device);
	}

	public void updateDevice(Device device) {
		Device entity = dao.findById(device.getId());
		if (entity != null) {
			entity.setName(device.getName());
		}
	}

	public void deleteById(int id) {
		dao.deleteById(id);
	}

	public boolean isDeviceUnique(String name) {
		Device entity = dao.findByName(name);
		return (entity == null || ((name != null) && entity.getName() == name));
	}
}
