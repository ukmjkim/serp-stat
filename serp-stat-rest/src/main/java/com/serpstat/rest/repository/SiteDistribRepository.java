package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.SiteDistrib;

public interface SiteDistribRepository extends JpaRepository<SiteDistrib, Long> {
	List<SiteDistrib> findAllBySiteId(long siteId);
	List<SiteDistrib> findAllBySiteIdAndCrawlDateGreaterThanEqualAndCrawlDateLessThanEqual(long siteId, int fromDate, int toDate);
	Optional<SiteDistrib> findBySiteIdAndCrawlDate(long siteId, int crawlDate);
}
