package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.TagSearchVolume;

public interface TagSearchVolumeService {
	TagSearchVolume findById(long id);

	List<TagSearchVolume> findAllByTagId(long tagId);

	List<TagSearchVolume> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate);

	TagSearchVolume findByTagIdAndCrawlDate(long tagId, int crawlDate);

	void saveTagSearchVolume(TagSearchVolume tagSearchVolume);

	void updateTagSearchVolume(TagSearchVolume tagSearchVolume);

	void deleteById(long id);

	boolean isTagSearchVolumeUnique(Long id, Long tagId, Integer crawlDate);

}