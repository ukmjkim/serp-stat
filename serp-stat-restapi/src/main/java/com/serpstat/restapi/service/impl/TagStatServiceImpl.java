package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.TagStatDao;
import com.serpstat.restapi.model.TagStat;
import com.serpstat.restapi.service.TagStatService;

@Service("tagStatService")
@Transactional
public class TagStatServiceImpl implements TagStatService {
	@Autowired
	TagStatDao dao;

	public TagStat findById(long id) {
		return dao.findById(id);
	}

	public List<TagStat> findAllByTagId(long tagId) {
		return dao.findAllByTagId(tagId);
	}

	public List<TagStat> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate) {
		return dao.findAllByTagIdInPeriod(tagId, fromDate, toDate);
	}

	public TagStat findByTagIdAndCrawlDate(long tagId, int crawlDate) {
		return dao.findByTagIdAndCrawlDate(tagId, crawlDate);
	}

	public void saveTagStat(TagStat tagStat) {
		dao.save(tagStat);
	}

	public void updateTagStat(TagStat tagStat) {
		TagStat entity = dao.findById(tagStat.getId());
		if (entity != null) {
			entity.setCrawlDate(tagStat.getCrawlDate());
			entity.setGoogleAverage(tagStat.getGoogleAverage());
			entity.setGoogleBaseAverage(tagStat.getGoogleBaseAverage());
			entity.setGoogleBaseRankingKeywords(tagStat.getGoogleBaseRankingKeywords());
			entity.setGoogleRankingKeywords(tagStat.getGoogleRankingKeywords());
			entity.setGoogleTopTenKeywords(tagStat.getGoogleTopTenKeywords());
			entity.setRankingKeywords(tagStat.getRankingKeywords());
			entity.setTotalKeywords(tagStat.getTotalKeywords());
			entity.setTrackedKeywords(tagStat.getTrackedKeywords());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isTagStatUnique(Long id, Long tagId, Integer crawlDate) {
		TagStat tagStat = dao.findByTagIdAndCrawlDate(tagId, crawlDate);
		return (tagStat == null || ((id != null) && tagStat.getId() == id));
	}
}
