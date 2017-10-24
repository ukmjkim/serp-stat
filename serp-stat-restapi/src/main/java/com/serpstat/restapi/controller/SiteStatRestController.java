package com.serpstat.restapi.controller;

import java.util.List;

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
import com.serpstat.restapi.exception.SiteStatNotFoundException;
import com.serpstat.restapi.exception.SiteStatNotUniqueInSiteException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.SiteStatService;

@RestController
public class SiteStatRestController {
	static final Logger logger = LoggerFactory.getLogger(SiteStatRestController.class);

	@Autowired
	SiteService siteService;

	@Autowired
	SiteStatService siteStatService;

	@RequestMapping(value = "/site/{siteId}/stat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SiteStat> getSiteStat(@PathVariable("siteId") long siteId, @PathVariable("id") long id) throws SiteStatNotFoundException {
		logger.info("Fetching Site Stat with id {}", id);
		SiteStat siteStat = siteStatService.findById(id);
		if (siteStat == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteStatNotFoundException("Site Stat with id not found");
		}

		return new ResponseEntity<SiteStat>(siteStat, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/stat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SiteStat>> listSiteStat(@PathVariable("siteId") long siteId,
		@RequestParam(required = true, value = "fromDate") int fromDate,
		@RequestParam(required = true, value = "toDate") int toDate) throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", siteId);
		Site site = siteService.findById(siteId);
		if (site == null) {
			logger.info("Site with id {} not found", siteId);
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Fetching SiteStat with id {}, fromDate {}, toDate {}", siteId, fromDate, toDate);
		List<SiteStat> siteStats = siteStatService.findAllBySiteIdInPeriod(siteId, fromDate, toDate);

		return new ResponseEntity<List<SiteStat>>(siteStats, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/stat", method = RequestMethod.POST)
	public ResponseEntity<Void> createSiteStat(@PathVariable("siteId") long siteId, @RequestBody SiteStat siteStat, UriComponentsBuilder ucBuilder)
			throws SiteNotFoundException, SiteStatNotUniqueInSiteException {
		logger.info("Fetching Site with id {}", siteStat.getSiteId());
		Site site = siteService.findById(siteStat.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", siteStat.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.debug("Creating Site Stat {}", siteStat);
		if (!(siteStatService.isSiteStatUnique(siteStat.getId(), siteStat.getSiteId(), siteStat.getCrawlDate()))) {
			logger.debug("A SiteStat with crawl date {} already exist", siteStat.getCrawlDate());
			throw new SiteStatNotUniqueInSiteException("The site stat given is already exist");
		}
		logger.debug("{}", siteStat);
		siteStatService.saveSiteStat(siteStat);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/site/{siteId}/stat").buildAndExpand(siteStat.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/site/{siteId}/stat", method = RequestMethod.PUT)
	public ResponseEntity<SiteStat> updateSiteStat(@PathVariable("siteId") long siteId, @RequestBody SiteStat siteStat)
			throws SiteNotFoundException, SiteStatNotFoundException {
		logger.info("Fetching Site with id {}", siteStat.getSiteId());
		Site site = siteService.findById(siteStat.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", siteStat.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Updating Site Stat {}", siteStat.getId());
		SiteStat currentSiteStat = siteStatService.findById(siteStat.getId());

		if (currentSiteStat == null) {
			logger.info("Site Stat with id {} not found", siteStat.getId());
			throw new SiteStatNotFoundException("Site Stat with id not found");
		}

		siteStatService.updateSiteStat(siteStat);
		return new ResponseEntity<SiteStat>(siteStat, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/stat/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<SiteStat> deleteSiteStat(@PathVariable("id") long id) throws SiteStatNotFoundException {
		logger.info("Fetching & Deleting Site Stat with id {}", id);
		SiteStat siteStat = siteStatService.findById(id);
		if (siteStat == null) {
			logger.info("Unable to delete. Site Stat with id {} not found", id);
			throw new SiteStatNotFoundException("Site Stat with id not found");
		}

		siteStatService.deleteById(id);
		return new ResponseEntity<SiteStat>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ SiteNotFoundException.class, SiteStatNotFoundException.class, SiteStatNotUniqueInSiteException.class })
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
