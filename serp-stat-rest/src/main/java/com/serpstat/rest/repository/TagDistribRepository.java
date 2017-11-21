package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.TagDistrib;

public interface TagDistribRepository extends JpaRepository<TagDistrib, Long> {
	List<TagDistrib> findAllByTagId(long tagId);
	List<TagDistrib> findAllByTagIdAndCrawlDateGreaterThanEqualAndCrawlDateLessThanEqual(long tagId, int fromDate, int toDate);
	Optional<TagDistrib> findByTagIdAndCrawlDate(long tagId, int crawlDate);
}
