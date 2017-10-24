package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.TagStat;

public interface TagStatDao {
	TagStat findById(long id);

	List<TagStat> findAllByTagId(long tagId);

	List<TagStat> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate);

	TagStat findByTagIdAndCrawlDate(long tagId, int crawlDate);

	void save(TagStat tagStat);

	void deleteById(long id);
}
