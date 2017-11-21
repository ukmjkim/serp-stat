package com.serpstat.rest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.serpstat.rest.domain.Site;

public interface SiteRepository extends JpaRepository<Site, Long> {
	List<Site> findAllByUserId(String login);
	Optional<Site> findByUserIdAndTitle(long userId, String title);
}
