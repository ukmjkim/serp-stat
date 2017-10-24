package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.TagSearchVolume;

public interface TagSearchVolumeDao {
	TagSearchVolume findById(long id);

	List<TagSearchVolume> findAllByTagId(long tagId);

	List<TagSearchVolume> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate);

	TagSearchVolume findByTagIdAndCrawlDate(long tagId, int crawlDate);

	void save(TagSearchVolume tagSearchVolume);

	void deleteById(long id);
}
