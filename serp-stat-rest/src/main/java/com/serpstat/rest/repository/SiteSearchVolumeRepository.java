package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.SiteSearchVolume;

public interface SiteSearchVolumeRepository extends JpaRepository<SiteSearchVolume, Long> {
	List<SiteSearchVolume> findAllBySiteId(long siteId);
	List<SiteSearchVolume> findAllBySiteIdAndCrawlDateGreaterThanEqualAndCrawlDateLessThanEqual(long siteId, int fromDate, int toDate);
	Optional<SiteSearchVolume> findBySiteIdAndCrawlDate(long siteId, int crawlDate);
}
