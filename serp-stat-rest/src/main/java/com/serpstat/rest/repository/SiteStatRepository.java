package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.SiteStat;

public interface SiteStatRepository extends JpaRepository<SiteStat, Long> {
	List<SiteStat> findAllBySiteId(long siteId);
	List<SiteStat> findAllBySiteIdAndCrawlDateGreaterThanEqualAndCrawlDateLessThanEqual(long siteId, int fromDate, int toDate);
	Optional<SiteStat> findBySiteIdAndCrawlDate(long siteId, int crawlDate);
}
