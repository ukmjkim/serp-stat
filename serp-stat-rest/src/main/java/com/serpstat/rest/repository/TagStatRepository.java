package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.TagStat;

public interface TagStatRepository extends JpaRepository<TagStat, Long> {
	List<TagStat> findAllByTagId(long tagId);
	List<TagStat> findAllByTagIdAndCrawlDateGreaterThanEqualAndCrawlDateLessThanEqual(long tagId, int fromDate, int toDate);
	Optional<TagStat> findByTagIdAndCrawlDate(long tagId, int crawlDate);
}
