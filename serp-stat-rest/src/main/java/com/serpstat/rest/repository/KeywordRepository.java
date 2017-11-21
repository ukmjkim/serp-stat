package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.Keyword;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
	List<Keyword> findAllBySiteId(long siteId);
	// List<Keyword> findPagenatedBySiteId(long siteId, int firstResult, int maxResults);
	int findTotalCountBySiteId(long siteId);
	Optional<Keyword> findBySiteIdAndKeyword(long siteId, String keyword);

}
