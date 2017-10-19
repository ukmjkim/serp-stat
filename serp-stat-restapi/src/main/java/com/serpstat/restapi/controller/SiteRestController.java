package com.serpstat.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.exception.SiteTitleNotUniqueException;
import com.serpstat.restapi.exception.UserAPINotFoundException;
import com.serpstat.restapi.exception.UserLoginNotUniqueException;
import com.serpstat.restapi.exception.UserNotFoundException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.TagService;

@RestController
public class SiteRestController {
	static final Logger logger = LoggerFactory.getLogger(SiteRestController.class);

	@Autowired
	SiteService siteService;

	@Autowired
	TagService tagService;

	@RequestMapping(value = "/site/", method = RequestMethod.GET)
	public ResponseEntity<List<Site>> listAllSites() {
		List<Site> sites = siteService.findAll();
		if (sites.isEmpty()) {
			return new ResponseEntity<List<Site>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Site>>(sites, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Site> getSite(@PathVariable("id") long id) throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", id);
		Site site = siteService.findById(id);
		if (site == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteNotFoundException("Site with id not found");
		}
		return new ResponseEntity<Site>(site, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{id}/tags", method = RequestMethod.GET)
	public ResponseEntity<List<Tag>> listAllTagsBySiteId(@PathVariable("id") long siteId) {
		List<Tag> tags = tagService.findAllBySiteId(siteId);
		if (tags.isEmpty()) {
			return new ResponseEntity<List<Tag>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
	}

	@ExceptionHandler({ SiteNotFoundException.class })
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
