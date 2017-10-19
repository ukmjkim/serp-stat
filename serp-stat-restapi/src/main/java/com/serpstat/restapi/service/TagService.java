package com.serpstat.restapi.service;

import java.util.List;

import com.serpstat.restapi.model.Tag;

public interface TagService {
	Tag findById(long id);

	List<Tag> findAllBySiteId(long siteId);

	List<Tag> findAllSiteIdAndMatchedTag(long siteId, String tag);

	void saveTag(Tag tag);

	void updateTag(Tag tag);

	void deleteById(long id);

	boolean isTagUnique(Long id, Long siteId, String tag);
}
