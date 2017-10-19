package com.serpstat.restapi.dao;

import java.util.List;

import com.serpstat.restapi.model.Tag;

public interface TagDao {
	Tag findById(long id);

	List<Tag> findAllBySiteId(long siteId);

	List<Tag> findAllSiteIdAndMatchedTag(long siteId, String tag);

	Tag findTagBySiteIdAndTag(long siteId, String tag);

	void save(Tag tag);

	void deleteById(long id);
}
