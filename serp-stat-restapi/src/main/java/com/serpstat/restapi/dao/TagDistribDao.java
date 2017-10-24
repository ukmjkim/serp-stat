package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.TagDistrib;

public interface TagDistribDao {
	TagDistrib findById(long id);

	List<TagDistrib> findAllByTagId(long tagId);

	List<TagDistrib> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate);

	TagDistrib findByTagIdAndCrawlDate(long tagId, int crawlDate);

	void save(TagDistrib tagDistrib);

	void deleteById(long id);
}
