package com.serpstat.restapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.exception.SiteDistribNotFoundException;
import com.serpstat.restapi.exception.SiteDistribNotUniqueInSiteException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteDistrib;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.SiteDistribService;

@RestController
public class SiteDistribRestController {
	static final Logger logger = LoggerFactory.getLogger(SiteDistribRestController.class);

	@Autowired
	SiteService siteService;

	@Autowired
	SiteDistribService siteDistribService;

	@RequestMapping(value = "/site/{siteId}/distrib/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SiteDistrib> getSiteDistrib(@PathVariable("siteId") long siteId, @PathVariable("id") long id) throws SiteDistribNotFoundException {
		logger.info("Fetching Site Stat with id {}", id);
		SiteDistrib siteDistrib = siteDistribService.findById(id);
		if (siteDistrib == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteDistribNotFoundException("Site Distrib with id not found");
		}

		return new ResponseEntity<SiteDistrib>(siteDistrib, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/distrib", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SiteDistrib>> listSiteDistrib(@PathVariable("siteId") long siteId,
		@RequestParam(required = true, value = "fromDate") int fromDate,
		@RequestParam(required = true, value = "toDate") int toDate) throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", siteId);
		Site site = siteService.findById(siteId);
		if (site == null) {
			logger.info("Site with id {} not found", siteId);
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Fetching SiteDistrib with id {}, fromDate {}, toDate {}", siteId, fromDate, toDate);
		List<SiteDistrib> siteDistribs = siteDistribService.findAllBySiteIdInPeriod(siteId, fromDate, toDate);

		return new ResponseEntity<List<SiteDistrib>>(siteDistribs, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/distrib", method = RequestMethod.POST)
	public ResponseEntity<Void> createSiteDistrib(@PathVariable("siteId") long siteId, @RequestBody SiteDistrib siteDistrib, UriComponentsBuilder ucBuilder)
			throws SiteNotFoundException, SiteDistribNotUniqueInSiteException {
		logger.info("Fetching Site with id {}", siteDistrib.getSiteId());
		Site site = siteService.findById(siteDistrib.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", siteDistrib.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.debug("Creating Site Distrib {}", siteDistrib);
		if (!(siteDistribService.isSiteDistribUnique(siteDistrib.getId(), siteDistrib.getSiteId(), siteDistrib.getCrawlDate()))) {
			logger.debug("A SiteDistrib with crawl date {} already exist", siteDistrib.getCrawlDate());
			throw new SiteDistribNotUniqueInSiteException("The site distrib given is already exist");
		}
		logger.debug("{}", siteDistrib);
		siteDistribService.saveSiteDistrib(siteDistrib);

		Map<String, Long> pathIds = new HashMap<>();
		pathIds.put("siteId", siteDistrib.getSiteId());
		pathIds.put("id", siteDistrib.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/site/{siteId}/distrib/{id}").buildAndExpand(pathIds).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/site/{siteId}/distrib/{id}", method = RequestMethod.PUT)
	public ResponseEntity<SiteDistrib> updateSiteDistrib(@PathVariable("siteId") long siteId, @PathVariable("id") long id, @RequestBody SiteDistrib siteDistrib)
			throws SiteNotFoundException, SiteDistribNotFoundException {
		logger.info("Fetching Site with id {}", siteDistrib.getSiteId());
		Site site = siteService.findById(siteDistrib.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", siteDistrib.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Updating Site Distrib {}", siteDistrib.getId());
		SiteDistrib currentSiteDistrib = siteDistribService.findById(siteDistrib.getId());

		if (currentSiteDistrib == null) {
			logger.info("Site Distrib with id {} not found", siteDistrib.getId());
			throw new SiteDistribNotFoundException("Site Distrib with id not found");
		}

		siteDistribService.updateSiteDistrib(siteDistrib);
		SiteDistrib entity = siteDistribService.findById(siteDistrib.getId());
		return new ResponseEntity<SiteDistrib>(entity, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/distrib/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<SiteDistrib> deleteSiteDistrib(@PathVariable("siteId") long siteId, @PathVariable("id") long id) throws SiteDistribNotFoundException {
		logger.info("Fetching & Deleting Site Distrib with id {}", id);
		SiteDistrib siteDistrib = siteDistribService.findById(id);
		if (siteDistrib == null) {
			logger.info("Unable to delete. Site Distrib with id {} not found", id);
			throw new SiteDistribNotFoundException("Site Distrib with id not found");
		}

		siteDistribService.deleteById(id);
		return new ResponseEntity<SiteDistrib>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ SiteNotFoundException.class, SiteDistribNotFoundException.class, SiteDistribNotUniqueInSiteException.class })
	public ResponseEntity<ExceptionInfo> handleException(Exception ex) {
		ExceptionInfo error = new ExceptionInfo();
		if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
			ResponseStatus rs = AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class);
			error.setErrorCode(Integer.parseInt(rs.value().toString()));
		} else {
			error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		}
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
	}
}
