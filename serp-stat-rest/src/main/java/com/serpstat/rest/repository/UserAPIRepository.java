package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.UserAPI;

public interface UserAPIRepository extends JpaRepository<UserAPI, Integer> {
	Optional<UserAPI> findByApiKey(String apiKey);
	List<UserAPI> findAllByUserId(long userId);
	void deleteByApiKey(String apiKey);
}
