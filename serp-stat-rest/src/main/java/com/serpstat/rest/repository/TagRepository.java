package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
	List<Tag> findAllBySiteId(long siteId);
	List<Tag> findAllBySiteIdAndTagLike(long siteId, String tag);
	Optional<Tag> findTagBySiteIdAndTag(long siteId, String tag);

}
