package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.TagSearchVolume;

public interface TagSearchVolumeRepository extends JpaRepository<TagSearchVolume, Long> {
	List<TagSearchVolume> findAllByTagId(long tagId);
	List<TagSearchVolume> findAllByTagIdAndCrawlDateGreaterThanEqualAndCrawlDateLessThanEqual(long tagId, int fromDate, int toDate);
	Optional<TagSearchVolume> findByTagIdAndCrawlDate(long tagId, int crawlDate);
}
