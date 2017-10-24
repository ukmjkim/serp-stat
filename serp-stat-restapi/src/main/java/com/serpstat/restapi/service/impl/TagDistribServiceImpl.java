package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.TagDistribDao;
import com.serpstat.restapi.model.TagDistrib;
import com.serpstat.restapi.service.TagDistribService;

@Service("tagDistribService")
@Transactional
public class TagDistribServiceImpl implements TagDistribService {
	@Autowired
	TagDistribDao dao;

	public TagDistrib findById(long id) {
		return dao.findById(id);
	}

	public List<TagDistrib> findAllByTagId(long tagId) {
		return dao.findAllByTagId(tagId);
	}

	public List<TagDistrib> findAllByTagIdInPeriod(long tagId, int fromDate, int toDate) {
		return dao.findAllByTagIdInPeriod(tagId, fromDate, toDate);
	}

	public TagDistrib findByTagIdAndCrawlDate(long tagId, int crawlDate) {
		return dao.findByTagIdAndCrawlDate(tagId, crawlDate);
	}

	public void saveTagDistrib(TagDistrib tagDistrib) {
		dao.save(tagDistrib);
	}

	public void updateTagDistrib(TagDistrib tagDistrib) {
		TagDistrib entity = dao.findById(tagDistrib.getId());
		if (entity != null) {
			entity.setOne(tagDistrib.getOne());
			entity.setTwo(tagDistrib.getTwo());
			entity.setThree(tagDistrib.getThree());
			entity.setFour(tagDistrib.getFour());
			entity.setFive(tagDistrib.getFive());
			entity.setSixToTen(tagDistrib.getSixToTen());
			entity.setElevenToTwenty(tagDistrib.getElevenToTwenty());
			entity.setTwentyOneToThirty(tagDistrib.getTwentyOneToThirty());
			entity.setThirtyOneToForty(tagDistrib.getThirtyOneToForty());
			entity.setFortyOneToFifty(tagDistrib.getFortyOneToFifty());
			entity.setFiftyOneToHundred(tagDistrib.getFiftyOneToHundred());
			entity.setNonRanking(tagDistrib.getNonRanking());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isTagDistribUnique(Long id, Long tagId, Integer crawlDate) {
		TagDistrib tagDistrib = dao.findByTagIdAndCrawlDate(tagId, crawlDate);
		return (tagDistrib == null || ((id != null) && tagDistrib.getId() == id));
	}

}
