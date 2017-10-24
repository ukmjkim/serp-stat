package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.serpstat.restapi.dao.TagSearchVolumeDao;
import com.serpstat.restapi.model.TagSearchVolume;
import com.serpstat.restapi.service.TagSearchVolumeService;

public class TagSearchVolumeServiceImpl implements TagSearchVolumeService {
	@Autowired
	TagSearchVolumeDao dao;

	public TagSearchVolume findById(long id) {
		return dao.findById(id);
	}

	public List<TagSearchVolume> findAllByTagId(long tagId) {
		return dao.findAllByTagId(tagId);
	}

	public List<TagSearchVolume> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate) {
		return dao.findAllByTagIdInPeriod(tagId, fromDate, toDate);
	}

	public TagSearchVolume findByTagIdAndCrawlDate(long tagId, int crawlDate) {
		return dao.findByTagIdAndCrawlDate(tagId, crawlDate);
	}

	public void saveTagSearchVolume(TagSearchVolume tagSearchVolume) {
		dao.save(tagSearchVolume);
	}

	public void updateTagSearchVolume(TagSearchVolume tagSearchVolume) {
		TagSearchVolume entity = dao.findById(tagSearchVolume.getId());
		if (entity != null) {
			entity.setGlobalAggregateSV(tagSearchVolume.getGlobalAggregateSV());
			entity.setRegionalAverageSV(tagSearchVolume.getGlobalAverageSV());
			entity.setRegionalAggregateSV(tagSearchVolume.getRegionalAggregateSV());
			entity.setRegionalAverageSV(tagSearchVolume.getRegionalAverageSV());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isTagSearchVolumeUnique(Long id, Long tagId, Integer crawlDate) {
		TagSearchVolume tagSearchVolume = dao.findByTagIdAndCrawlDate(tagId, crawlDate);
		return (tagSearchVolume == null || ((id != null) && tagSearchVolume.getId() == id));
	}

}
