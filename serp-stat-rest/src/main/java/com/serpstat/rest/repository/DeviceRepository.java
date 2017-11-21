package com.serpstat.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {
	Optional<Device> findByName(String name);
}
