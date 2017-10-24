package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.TagDistrib;

public interface TagDistribService {
	TagDistrib findById(long id);

	List<TagDistrib> findAllByTagId(long tagId);

	List<TagDistrib> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate);

	TagDistrib findByTagIdAndCrawlDate(long tagId, int crawlDate);

	void saveTagDistrib(TagDistrib tagDistrib);

	void updateTagDistrib(TagDistrib tagDistrib);

	void deleteById(long id);

	boolean isTagDistribUnique(Long id, Long tagId, Integer crawlDate);

}
