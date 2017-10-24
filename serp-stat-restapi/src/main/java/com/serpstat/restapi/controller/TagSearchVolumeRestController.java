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
import com.serpstat.restapi.exception.TagSearchVolumeNotUniqueInTagException;
import com.serpstat.restapi.exception.TagStatNotFoundException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.model.TagSearchVolume;
import com.serpstat.restapi.model.TagStat;
import com.serpstat.restapi.service.TagService;
import com.serpstat.restapi.service.TagSearchVolumeService;

@RestController
public class TagSearchVolumeRestController {
	static final Logger logger = LoggerFactory.getLogger(TagSearchVolumeRestController.class);

	@Autowired
	TagService tagService;

	@Autowired
	TagSearchVolumeService tagSearchVolumeService;

	@RequestMapping(value = "/tag/{tagId}/searchvolume/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TagSearchVolume> getTagSearchVolume(@PathVariable("tagId") long tagId, @PathVariable("id") long id) throws TagSearchVolumeNotFoundException {
		logger.info("Fetching Tag Search Volume with id {}", id);
		TagSearchVolume tagSearchVolume = tagSearchVolumeService.findById(id);
		if (tagSearchVolume == null) {
			logger.info("Tag Search Volume with id {} not found", id);
			throw new TagSearchVolumeNotFoundException("Tag Search Volume with id not found");
		}

		return new ResponseEntity<TagSearchVolume>(tagSearchVolume, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/searchvolume", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TagSearchVolume>> listTagSearchVolume(@PathVariable("tagId") long tagId,
		@RequestParam(required = true, value = "fromDate") int fromDate,
		@RequestParam(required = true, value = "toDate") int toDate) throws TagNotFoundException {
		logger.info("Fetching Tag with id {}", tagId);
		Tag tag = tagService.findById(tagId);
		if (tag == null) {
			logger.info("Tag with id {} not found", tagId);
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.info("Fetching TagSearchVolume with id {}, fromDate {}, toDate {}", tagId, fromDate, toDate);
		List<TagSearchVolume> tagSearchVolumes = tagSearchVolumeService.findAllByTagIdInPeriod(tagId, fromDate, toDate);

		return new ResponseEntity<List<TagSearchVolume>>(tagSearchVolumes, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/searchvolume", method = RequestMethod.POST)
	public ResponseEntity<Void> createTagSearchVolume(@PathVariable("tagId") long tagId,
			@RequestBody TagSearchVolume tagSearchVolume, UriComponentsBuilder ucBuilder)
			throws TagNotFoundException, TagSearchVolumeNotUniqueInTagException {
		logger.info("Fetching Tag with id {}", tagSearchVolume.getTagId());
		Tag tag = tagService.findById(tagSearchVolume.getTagId());
		if (tag == null) {
			logger.info("Tag with id {} not found", tagSearchVolume.getTagId());
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.debug("Creating Tag {}", tagSearchVolume);
		if (!(tagSearchVolumeService.isTagSearchVolumeUnique(tagSearchVolume.getId(), tagSearchVolume.getTagId(), tagSearchVolume.getCrawlDate()))) {
			logger.debug("A TagSearchVolume with crawl date {} already exist", tagSearchVolume.getCrawlDate());
			throw new TagSearchVolumeNotUniqueInTagException("The tag stat given is already exist");
		}
		logger.debug("{}", tagSearchVolume);
		tagSearchVolumeService.saveTagSearchVolume(tagSearchVolume);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tag/{tagId}/searchvolume").buildAndExpand(tagSearchVolume.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tag/{tagId}/searchvolume", method = RequestMethod.PUT)
	public ResponseEntity<TagSearchVolume> updateTagSearchVolume(@PathVariable("tagId") long tagId, @RequestBody TagSearchVolume tagSearchVolume)
			throws TagNotFoundException, TagSearchVolumeNotFoundException {
		logger.info("Fetching Tag with id {}", tagSearchVolume.getTagId());
		Tag tag = tagService.findById(tagSearchVolume.getTagId());
		if (tag == null) {
			logger.info("Tag with id {} not found", tagSearchVolume.getTagId());
			throw new TagNotFoundException("Tag with id not found");
		}

		logger.info("Updating Tag SearchVolume {}", tagSearchVolume.getId());
		TagSearchVolume currentTagSearchVolume = tagSearchVolumeService.findById(tagSearchVolume.getId());

		if (currentTagSearchVolume == null) {
			logger.info("Tag SearchVolume with id {} not found", tagSearchVolume.getId());
			throw new TagSearchVolumeNotFoundException("Tag SearchVolume with id not found");
		}

		tagSearchVolumeService.updateTagSearchVolume(tagSearchVolume);
		return new ResponseEntity<TagSearchVolume>(tagSearchVolume, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{tagId}/searchvolume/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<TagSearchVolume> deleteTagSearchVolume(@PathVariable("id") long id) throws TagSearchVolumeNotFoundException {
		logger.info("Fetching & Deleting Tag SearchVolume with id {}", id);
		TagSearchVolume tagSearchVolume = tagSearchVolumeService.findById(id);
		if (tagSearchVolume == null) {
			logger.info("Unable to delete. Tag SearchVolume with id {} not found", id);
			throw new TagSearchVolumeNotFoundException("Tag SearchVolume with id not found");
		}

		tagSearchVolumeService.deleteById(id);
		return new ResponseEntity<TagSearchVolume>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ TagNotFoundException.class, TagNotUniqueInSiteException.class, TagSearchVolumeNotFoundException.class, TagSearchVolumeNotUniqueInTagException.class })
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
