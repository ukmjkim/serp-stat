package com.serpstat.rest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.Market;

public interface MarketRepository extends JpaRepository<Market, Integer> {
	Optional<Market> findByRegionAndLang(String region, String lang);
	void deleteByRegionAndLang(String region, String lang);

}
