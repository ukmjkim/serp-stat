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

import com.serpstat.restapi.exception.SiteStatNotFoundException;
import com.serpstat.restapi.exception.TagNotFoundException;
import com.serpstat.restapi.exception.TagStatNotFoundException;
import com.serpstat.restapi.exception.TagStatNotUniqueInTagException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.SiteStat;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.TagStat;
import com.serpstat.restapi.service.TagService;
import com.serpstat.restapi.service.TagStatService;

@RestController
public class TagStatRestController {
	static final Logger logger = LoggerFactory.getLogger(TagStatRestController.class);

	@Autowired
	TagService tagService;

	@Autowired
	TagStatService tagStatService;

	@RequestMapping(value = "/tag/{tagId}/stat/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TagStat> getTagStat(@PathVariable("tagId") long tagId, @PathVariable("id") long id) throws TagStatNotFoundException {
		logger.info("Fetching Tag Stat with id {}", id);
		TagStat tagStat = tagStatService.findById(id);
		if (tagStat == null) {
			logger.info("Tag Stat with id {} not found", id);
			throw new TagStatNotFoundException("Tag Stat with id not found");
		}

		return new ResponseEntity<TagStat>(tagStat, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/stat", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TagStat>> listTagStat(@PathVariable("tagId") long tagId,
			@RequestParam(required = true, value = "fromDate") int fromDate,
			@RequestParam(required = true, value = "toDate") int toDate) throws TagNotFoundException {
		logger.info("Fetching Tag with id {}", tagId);
		Tag tag = tagService.findById(tagId);
		if (tag == null) {
			logger.info("Tag with id {} not found", tagId);
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.info("Fetching TagStat with id {}, fromDate {}, toDate {}", tagId, fromDate, toDate);
		List<TagStat> tagStats = tagStatService.findAllByTagIdInPeriod(tagId, fromDate, toDate);

		return new ResponseEntity<List<TagStat>>(tagStats, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/stat", method = RequestMethod.POST)
	public ResponseEntity<Void> createTagStat(@PathVariable("tagId") long tagId, @RequestBody TagStat tagStat,
			UriComponentsBuilder ucBuilder) throws TagNotFoundException, TagStatNotUniqueInTagException {
		logger.info("Fetching Tag with id {}", tagStat.getTagId());
		Tag tag = tagService.findById(tagStat.getTagId());
		if (tag == null) {
			logger.info("Tag with id {} not found", tagStat.getTagId());
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.debug("Creating Tag {}", tagStat);
		if (!(tagStatService.isTagStatUnique(tagStat.getId(), tagStat.getTagId(), tagStat.getCrawlDate()))) {
			logger.debug("A TagStat with crawl date {} already exist", tagStat.getCrawlDate());
			throw new TagStatNotUniqueInTagException("The tag stat given is already exist");
		}
		logger.debug("{}", tagStat);
		tagStatService.saveTagStat(tagStat);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tag/{tagId}/stat/{id}").buildAndExpand(tagStat.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tag/{tagId}/stat/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TagStat> updateTagStat(@PathVariable("tagId") long tagId, @PathVariable("id") long id, @RequestBody TagStat tagStat)
			throws TagNotFoundException, TagStatNotFoundException {
		logger.info("Fetching Tag with id {}", tagStat.getTagId());
		Tag tag = tagService.findById(tagStat.getTagId());
		if (tag == null) {
			logger.info("Tag with id {} not found", tagStat.getTagId());
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.info("Updating Tag Stat {}", tagStat.getId());
		TagStat currentTagStat = tagStatService.findById(tagStat.getId());

		if (currentTagStat == null) {
			logger.info("Tag Stat with id {} not found", tagStat.getId());
			throw new TagStatNotFoundException("Tag Stat with id not found");
		}

		tagStatService.updateTagStat(tagStat);
		return new ResponseEntity<TagStat>(tagStat, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/stat/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<TagStat> deleteTagStat(@PathVariable("tagId") long tagId, @PathVariable("id") long id) throws TagStatNotFoundException {
		logger.info("Fetching & Deleting Tag Stat with id {}", id);
		TagStat tagStat = tagStatService.findById(id);
		if (tagStat == null) {
			logger.info("Unable to delete. Tag Stat with id {} not found", id);
			throw new TagStatNotFoundException("Tag Stat with id not found");
		}

		tagStatService.deleteById(id);
		return new ResponseEntity<TagStat>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ TagNotFoundException.class, TagStatNotFoundException.class,
			TagStatNotUniqueInTagException.class })
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
