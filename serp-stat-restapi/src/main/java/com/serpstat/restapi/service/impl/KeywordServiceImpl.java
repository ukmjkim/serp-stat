package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.KeywordDao;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.service.KeywordService;

@Service("keywordService")
@Transactional
public class KeywordServiceImpl implements KeywordService {
	@Autowired
	KeywordDao dao;

	public Keyword findById(long id) {
		return dao.findById(id);
	}

	public List<Keyword> findAllBySiteId(long siteId) {
		return dao.findAllBySiteId(siteId);
	}

	public List<Keyword> findPagenatedBySiteId(long siteId, int firstResult, int maxResults) {
		return dao.findPagenatedBySiteId(siteId, firstResult, maxResults);
	}

	public int findTotalCountBySiteId(long siteId) {
		return dao.findTotalCountBySiteId(siteId);
	}

	public Keyword findBySiteIdAndKeyword(long siteId, String keyword) {
		return dao.findBySiteIdAndKeyword(siteId, keyword);
	}

	public void saveKeyword(Keyword keyword) {
		dao.save(keyword);
	}

	public void updateKeyword(Keyword keyword) {
		Keyword entity = dao.findById(keyword.getId());
		if (entity != null) {
			entity.setKeyword(keyword.getKeyword());
			entity.setTranslation(keyword.getTranslation());
			entity.setChecksum(keyword.getChecksum());
			entity.setTracking(keyword.getTracking());
			entity.setLocation(keyword.getLocation());
			entity.setDeleted(keyword.getDeleted());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isKeywordInSiteUnique(Long siteId, String keyword) {
		Keyword entity = dao.findBySiteIdAndKeyword(siteId, keyword);
		return (entity == null) || ((siteId != null && keyword != null)
				&& (entity.getSiteId() == siteId && entity.getKeyword() == keyword));
	}
}
