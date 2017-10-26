package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.Device;

public interface DeviceService {
	Device findById(int id);

	List<Device> findAll();

	void saveDevice(Device device);

	void updateDevice(Device device);

	void deleteById(int id);

	boolean isDeviceUnique(String name);
}
