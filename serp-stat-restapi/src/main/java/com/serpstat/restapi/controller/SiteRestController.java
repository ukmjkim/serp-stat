package com.serpstat.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.service.SiteService;

@RestController
public class SiteRestController {
	static final Logger logger = LoggerFactory.getLogger(SiteRestController.class);
	
	@Autowired
	SiteService siteService;

	@RequestMapping(value = "/site/", method = RequestMethod.GET)
	public ResponseEntity<List<Site>> listAllSites() {
		List<Site> sites = siteService.findAll();
		if (sites.isEmpty()) {
			return new ResponseEntity<List<Site>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Site> getSite(@PathVariable("id") long id) {
		logger.info("Fetching Site with id {}", id);
		Site site = siteService.findById(id);
		if (site == null) {
			logger.info("Site with id {} not found", id);
			return new ResponseEntity<Site>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Site>(site, HttpStatus.OK);
	}
}
