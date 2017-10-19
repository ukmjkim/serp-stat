package com.serpstat.restapi.controller;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.serpstat.restapi.exception.TagNotFoundException;
import com.serpstat.restapi.exception.TagNotUniqueInSiteException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Tag;
import com.serpstat.restapi.service.TagService;

@RestController
public class TagRestController {
	static final Logger logger = LoggerFactory.getLogger(TagRestController.class);

	@Autowired
	TagService tagService;

	@RequestMapping(value = "/tag/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Tag> getTag(@PathVariable("id") long id) throws TagNotFoundException {
		logger.info("Fetching Tag with id {}", id);
		Tag tag = tagService.findById(id);
		if (tag == null) {
			logger.info("Tag with id {} not found", id);
			throw new TagNotFoundException("Tag with id not found");
		}
		return new ResponseEntity<Tag>(tag, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/", method = RequestMethod.POST)
	public ResponseEntity<Void> createTag(@RequestBody Tag tag, UriComponentsBuilder ucBuilder)
			throws TagNotUniqueInSiteException {
		logger.debug("Creating Tag {}", tag.getTag());
		if (!(tagService.isTagUnique(tag.getId(), tag.getSiteId(), tag.getTag()))) {
			logger.debug("A Tag with login {} already exist", tag.getTag());
			throw new TagNotUniqueInSiteException("The tag given is already exist");
		}
		logger.debug("{}", tag);
		tagService.saveTag(tag);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/tag/{id}").buildAndExpand(tag.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/tag/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Tag> updateTag(@PathVariable("id") long id, @RequestBody Tag tag)
			throws TagNotFoundException {
		logger.info("Updating Tag {}", id);
		Tag currentTag = tagService.findById(id);

		if (currentTag == null) {
			logger.info("Tag with id {} not found", id);
			throw new TagNotFoundException("Tag with id not found");
		}

		tagService.updateTag(tag);
		return new ResponseEntity<Tag>(currentTag, HttpStatus.OK);
	}

	@RequestMapping(value = "/tag/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Tag> deleteTag(@PathVariable("id") long id) throws TagNotFoundException {
		logger.info("Fetching & Deleting Tag with id {}", id);
		Tag tag = tagService.findById(id);
		if (tag == null) {
			logger.info("Unable to delete. Tag with id {} not found", id);
			throw new TagNotFoundException("Tag with id not found");
		}

		tagService.deleteById(id);
		return new ResponseEntity<Tag>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ TagNotFoundException.class, TagNotUniqueInSiteException.class })
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
