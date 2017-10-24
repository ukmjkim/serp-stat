package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.TagStat;

public interface TagStatService {
	TagStat findById(long id);

	List<TagStat> findAllByTagId(long tagId);

	List<TagStat> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate);

	TagStat findByTagIdAndCrawlDate(long tagId, int crawlDate);

	void saveTagStat(TagStat tagStat);

	void updateTagStat(TagStat tagStat);

	void deleteById(long id);

	boolean isTagStatUnique(Long id, Long tagId, Integer crawlDate);

}
