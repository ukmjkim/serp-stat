package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.Keyword;

public interface KeywordDao {
	Keyword findById(long id);

	List<Keyword> findAllBySiteId(long siteId);

	List<Keyword> findPagenatedBySiteId(long siteId, int firstResult, int maxResults);

	int findTotalCountBySiteId(long siteId);

	Keyword findBySiteIdAndKeyword(long siteId, String keyword);

	void save(Keyword keyword);

	void deleteById(long id);
}
