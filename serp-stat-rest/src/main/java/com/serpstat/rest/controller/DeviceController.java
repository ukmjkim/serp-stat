package com.serpstat.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serpstat.rest.domain.ExceptionInfo;
import com.serpstat.rest.domain.Device;
import com.serpstat.rest.exception.DeviceNotFoundException;
import com.serpstat.rest.exception.DeviceNameNotUniqueException;
import com.serpstat.rest.repository.DeviceRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class DeviceController {

	@Autowired
	DeviceRepository deviceRepository;
	
	@GetMapping("/devices")
	public ResponseEntity<List<Device>> listAllDevices() {
		List<Device> devices = deviceRepository.findAll();
		if (devices.isEmpty()) {
			return new ResponseEntity<List<Device>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Device>>(devices, HttpStatus.OK);
	}
	
	@GetMapping("/devices/{deviceId}")
	public ResponseEntity<Device> getDevice(@PathVariable Integer deviceId) {
		Device device = deviceRepository.findById(deviceId).orElse(null);
		if (device == null) {
			return new ResponseEntity<Device>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Device>(device, HttpStatus.OK);		
	}
	
	@PostMapping("/devices")
	public ResponseEntity<Void> createDevice(@RequestBody Device device) throws DeviceNameNotUniqueException {
		Device entity = deviceRepository.findByName(device.getName()).orElse(null);
		if (entity != null) {
			log.info("The device name given is already exist []", device.getName());
			throw new DeviceNameNotUniqueException("The device name given is already exist");
		}

		Device createdEntity = deviceRepository.saveAndFlush(device);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEntity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/devices/{deviceId}")
	public ResponseEntity<Device> updateDevice(@PathVariable Integer deviceId, @RequestBody Device device) throws DeviceNotFoundException {
		Device entity = deviceRepository.findById(device.getId()).orElse(null);
		if (entity == null) {
			throw new DeviceNotFoundException("Device with id not found");
		}

		Device updatedEntity = deviceRepository.saveAndFlush(device);
		return new ResponseEntity<Device>(updatedEntity, HttpStatus.OK);
	}
	
	@DeleteMapping("/devices/{deviceId}")
	public ResponseEntity<Void> deleteDevice(@PathVariable Integer deviceId) throws DeviceNotFoundException {
		Device entity = deviceRepository.findById(deviceId).orElse(null);
		if (entity == null) {
			throw new DeviceNotFoundException("Device with id not found");
		}

		deviceRepository.deleteById(deviceId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ DeviceNotFoundException.class, DeviceNameNotUniqueException.class })
	public ResponseEntity<ExceptionInfo> handleException(Exception ex) {
		ExceptionInfo error = new ExceptionInfo();
		if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
			ResponseStatus rs = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
			error.setErrorCode(Integer.parseInt(rs.value().toString()));
		} else {
			error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		}
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
	}
}
