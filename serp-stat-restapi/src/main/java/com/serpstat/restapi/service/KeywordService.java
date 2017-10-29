package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.Keyword;

public interface KeywordService {
	Keyword findById(long id);

	List<Keyword> findAllBySiteId(long siteId);

	List<Keyword> findPagenatedBySiteId(long siteId, int firstResult, int maxResults);

	int findTotalCountBySiteId(long siteId);

	Keyword findBySiteIdAndKeyword(long siteId, String keyword);

	void saveKeyword(Keyword keyword);

	void updateKeyword(Keyword keyword);

	void deleteById(long id);

	boolean isKeywordInSiteUnique(Long siteId, String keyword);
}
