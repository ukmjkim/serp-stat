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

import com.serpstat.restapi.exception.TagNotFoundException;
import com.serpstat.restapi.exception.TagNotUniqueInSiteException;
import com.serpstat.restapi.exception.TagSearchVolumeNotFoundException;
import com.serpstat.restapi.exception.TagDistribNotFoundException;
import com.serpstat.restapi.exception.TagDistribNotUniqueInTagException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.TagDistrib;
import com.serpstat.restapi.model.TagSearchVolume;
import com.serpstat.restapi.service.TagService;
import com.serpstat.restapi.service.TagDistribService;

@RestController
public class TagDistribRestController {
	static final Logger logger = LoggerFactory.getLogger(TagDistribRestController.class);

	@Autowired
	TagService tagService;

	@Autowired
	TagDistribService tagDistribService;

	@RequestMapping(value = "/tag/{tagId}/distrib/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TagDistrib> getTagDistrib(@PathVariable("tagId") long tagId, @PathVariable("id") long id) throws TagDistribNotFoundException {
		logger.info("Fetching Tag Distrib with id {}", id);
		TagDistrib tagDistrib = tagDistribService.findById(id);
		if (tagDistrib == null) {
			logger.info("Tag Distrib with id {} not found", id);
			throw new TagDistribNotFoundException("Tag Distrib with id not found");
		}

		return new ResponseEntity<TagDistrib>(tagDistrib, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/distrib", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TagDistrib>> listTagDistrib(@PathVariable("tagId") long tagId,
			@RequestParam(required = true, value = "fromDate") int fromDate,
			@RequestParam(required = true, value = "toDate") int toDate) throws TagNotFoundException {
		logger.info("Fetching Tag with id {}", tagId);
		Tag tag = tagService.findById(tagId);
		if (tag == null) {
			logger.info("Tag with id {} not found", tagId);
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.info("Fetching TagDistrib with id {}, fromDate {}, toDate {}", tagId, fromDate, toDate);
		List<TagDistrib> tagDistribs = tagDistribService.findAllByTagIdInPeriod(tagId, fromDate, toDate);

		return new ResponseEntity<List<TagDistrib>>(tagDistribs, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/distrib", method = RequestMethod.POST)
	public ResponseEntity<Void> createTagDistrib(@PathVariable("tagId") long tagId, @RequestBody TagDistrib tagDistrib,
			UriComponentsBuilder ucBuilder) throws TagNotFoundException, TagDistribNotUniqueInTagException {
		logger.info("Fetching Tag with id {}", tagDistrib.getTagId());
		Tag tag = tagService.findById(tagDistrib.getTagId());
		if (tag == null) {
			logger.info("Tag with id {} not found", tagDistrib.getTagId());
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.debug("Creating Tag {}", tagDistrib);
		if (!(tagDistribService.isTagDistribUnique(tagDistrib.getId(), tagDistrib.getTagId(),
				tagDistrib.getCrawlDate()))) {
			logger.debug("A TagDistrib with crawl date {} already exist", tagDistrib.getCrawlDate());
			throw new TagDistribNotUniqueInTagException("The tag stat given is already exist");
		}
		logger.debug("{}", tagDistrib);
		tagDistribService.saveTagDistrib(tagDistrib);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tag/{tagId}/distrib").buildAndExpand(tagDistrib.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tag/{tagId}/distrib", method = RequestMethod.PUT)
	public ResponseEntity<TagDistrib> updateTagDistrib(@PathVariable("tagId") long tagId,
			@RequestBody TagDistrib tagDistrib) throws TagNotFoundException, TagDistribNotFoundException {
		logger.info("Fetching Tag with id {}", tagDistrib.getTagId());
		Tag tag = tagService.findById(tagDistrib.getTagId());
		if (tag == null) {
			logger.info("Tag with id {} not found", tagDistrib.getTagId());
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.info("Updating Tag Distrib {}", tagDistrib.getId());
		TagDistrib currentTagDistrib = tagDistribService.findById(tagDistrib.getId());

		if (currentTagDistrib == null) {
			logger.info("Tag Distrib with id {} not found", tagDistrib.getId());
			throw new TagDistribNotFoundException("Tag Distrib with id not found");
		}

		tagDistribService.updateTagDistrib(tagDistrib);
		return new ResponseEntity<TagDistrib>(tagDistrib, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/distrib/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<TagDistrib> deleteTagDistrib(@PathVariable("id") long id) throws TagDistribNotFoundException {
		logger.info("Fetching & Deleting Tag Distrib with id {}", id);
		TagDistrib tagDistrib = tagDistribService.findById(id);
		if (tagDistrib == null) {
			logger.info("Unable to delete. Tag Distrib with id {} not found", id);
			throw new TagDistribNotFoundException("Tag Distrib with id not found");
		}

		tagDistribService.deleteById(id);
		return new ResponseEntity<TagDistrib>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ TagNotFoundException.class, TagNotUniqueInSiteException.class,
			TagDistribNotFoundException.class, TagDistribNotUniqueInTagException.class })
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
