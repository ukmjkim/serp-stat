package com.serpstat.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

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
	@RequestMapping(value = "/site/", method = RequestMethod.POST)
	public ResponseEntity<Void> createSite(@RequestBody Site site, UriComponentsBuilder ucBuilder) {
		logger.debug("Creating Site {}", site.getTitle());
		if (siteService.isSiteTitleUnique(site.getId(), site.getUser().getId(), site.getTitle())) {
			logger.debug("A Site with title {} already exist", site.getTitle());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
        logger.debug("{}", site);
        siteService.saveSite(site);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/site/{id}").buildAndExpand(site.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);

	}
	@RequestMapping(value = "/site/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Site> updateSite(@PathVariable("id") long id, @RequestBody Site site) {
		logger.info("Updating Site {}", id);
		Site currentSite = siteService.findById(id);
        
        if (currentSite==null) {
        		logger.info("Site with id {} not found", id);
            return new ResponseEntity<Site>(HttpStatus.NOT_FOUND);
        }
         
        siteService.updateSite(site);
        return new ResponseEntity<Site>(currentSite, HttpStatus.OK);
		
	}
	@RequestMapping(value = "/site/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Site> deletSite(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting Site with id {}", id);
		Site site = siteService.findById(id);
        if (site == null) {
        		logger.info("Unable to delete. Site with id {} not found", id);
            return new ResponseEntity<Site>(HttpStatus.NOT_FOUND);
        }
 
        siteService.deleteById(id);
        return new ResponseEntity<Site>(HttpStatus.NO_CONTENT);
	}
}
