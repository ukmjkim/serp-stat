package com.serpstat.restapi.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.serpstat.restapi.dao.TagDao;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.service.TagService;

@Service("tagService")
@Transactional
public class TagServiceImpl implements TagService {
	@Autowired
	TagDao dao;

	public Tag findById(long id) {
		return dao.findById(id);
	}

	public List<Tag> findAllBySiteId(long siteId) {
		return dao.findAllBySiteId(siteId);
	}

	public List<Tag> findAllSiteIdAndMatchedTag(long siteId, String tag) {
		return dao.findAllSiteIdAndMatchedTag(siteId, tag);
	}

	public void saveTag(Tag tag) {
		dao.save(tag);
	}

	public void updateTag(Tag tag) {
		Tag entity = findById(tag.getId());
		if (entity != null) {
			entity.setTag(tag.getTag());
			entity.setBackfillId(tag.getBackfillId());
			entity.setFilterRefresh(tag.getFilterRefresh());
			entity.setReportTag(tag.getReportTag());
		}
	}

	public void deleteById(long id) {
		dao.deleteById(id);
	}

	public boolean isTagUnique(Long id, Long siteId, String tag) {
		Tag entity = dao.findTagBySiteIdAndTag(siteId, tag);
		return (entity == null || ((id != null) && entity.getId() == id));
	}
}
