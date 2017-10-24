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
import com.serpstat.restapi.exception.SiteSearchVolumeNotFoundException;
import com.serpstat.restapi.exception.SiteSearchVolumeNotUniqueInSiteException;
import com.serpstat.restapi.exception.SiteStatNotFoundException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteSearchVolume;
import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.SiteSearchVolumeService;

@RestController
public class SiteSearchVolumeRestController {
	static final Logger logger = LoggerFactory.getLogger(SiteSearchVolumeRestController.class);

	@Autowired
	SiteService siteService;

	@Autowired
	SiteSearchVolumeService siteSearchVolumeService;

	@RequestMapping(value = "/site/{siteId}/searchvolume/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<SiteSearchVolume> getSiteSearchVolume(@PathVariable("siteId") long siteId, @PathVariable("id") long id) throws SiteSearchVolumeNotFoundException {
		logger.info("Fetching Site Stat with id {}", id);
		SiteSearchVolume siteSearchVolume = siteSearchVolumeService.findById(id);
		if (siteSearchVolume == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteSearchVolumeNotFoundException("Site Search Volume with id not found");
		}

		return new ResponseEntity<SiteSearchVolume>(siteSearchVolume, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/searchvolume", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<SiteSearchVolume>> listSiteSearchVolume(@PathVariable("siteId") long siteId,
		@RequestParam(required = true, value = "fromDate") int fromDate,
		@RequestParam(required = true, value = "toDate") int toDate) throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", siteId);
		Site site = siteService.findById(siteId);
		if (site == null) {
			logger.info("Site with id {} not found", siteId);
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Fetching SiteSearchVolume with id {}, fromDate {}, toDate {}", siteId, fromDate, toDate);
		List<SiteSearchVolume> siteSearchVolumes = siteSearchVolumeService.findAllBySiteIdInPeriod(siteId, fromDate, toDate);

		return new ResponseEntity<List<SiteSearchVolume>>(siteSearchVolumes, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/searchvolume", method = RequestMethod.POST)
	public ResponseEntity<Void> createSiteSearchVolume(@PathVariable("siteId") long siteId, @RequestBody SiteSearchVolume siteSearchVolume, UriComponentsBuilder ucBuilder)
			throws SiteNotFoundException, SiteSearchVolumeNotUniqueInSiteException {
		logger.info("Fetching Site with id {}", siteSearchVolume.getSiteId());
		Site site = siteService.findById(siteSearchVolume.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", siteSearchVolume.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.debug("Creating Site Search Volume {}", siteSearchVolume);
		if (!(siteSearchVolumeService.isSiteSearchVolumeUnique(siteSearchVolume.getId(), siteSearchVolume.getSiteId(), siteSearchVolume.getCrawlDate()))) {
			logger.debug("A SiteSearchVolume with crawl date {} already exist", siteSearchVolume.getCrawlDate());
			throw new SiteSearchVolumeNotUniqueInSiteException("The site search volume given is already exist");
		}
		logger.debug("{}", siteSearchVolume);
		siteSearchVolumeService.saveSiteSearchVolume(siteSearchVolume);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/site/{siteId}/searchvolume").buildAndExpand(siteSearchVolume.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/site/{siteId}/searchvolume", method = RequestMethod.PUT)
	public ResponseEntity<SiteSearchVolume> updateSiteSearchVolume(@PathVariable("siteId") long siteId, @RequestBody SiteSearchVolume siteSearchVolume)
			throws SiteNotFoundException, SiteSearchVolumeNotFoundException {
		logger.info("Fetching Site with id {}", siteSearchVolume.getSiteId());
		Site site = siteService.findById(siteSearchVolume.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", siteSearchVolume.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Updating Site Search Volume {}", siteSearchVolume.getId());
		SiteSearchVolume currentSiteSearchVolume = siteSearchVolumeService.findById(siteSearchVolume.getId());

		if (currentSiteSearchVolume == null) {
			logger.info("Site Search Volume with id {} not found", siteSearchVolume.getId());
			throw new SiteSearchVolumeNotFoundException("Site Search Volume with id not found");
		}

		siteSearchVolumeService.updateSiteSearchVolume(siteSearchVolume);
		return new ResponseEntity<SiteSearchVolume>(siteSearchVolume, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/searchvolume/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<SiteSearchVolume> deleteSiteSearchVolume(@PathVariable("id") long id) throws SiteSearchVolumeNotFoundException {
		logger.info("Fetching & Deleting Site Search Volume with id {}", id);
		SiteSearchVolume siteSearchVolume = siteSearchVolumeService.findById(id);
		if (siteSearchVolume == null) {
			logger.info("Unable to delete. Site Search Volume with id {} not found", id);
			throw new SiteSearchVolumeNotFoundException("Site Search Volume with id not found");
		}

		siteSearchVolumeService.deleteById(id);
		return new ResponseEntity<SiteSearchVolume>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ SiteNotFoundException.class, SiteSearchVolumeNotFoundException.class, SiteSearchVolumeNotUniqueInSiteException.class })
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
