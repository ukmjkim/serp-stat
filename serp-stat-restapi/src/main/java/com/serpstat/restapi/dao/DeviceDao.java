package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.Device;

public interface DeviceDao {
	Device findById(int id);

	Device findByName(String name);

	List<Device> findAll();

	void save(Device device);

	void deleteById(int id);
}