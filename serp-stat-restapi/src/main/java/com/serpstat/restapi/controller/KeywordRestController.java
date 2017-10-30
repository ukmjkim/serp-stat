package com.serpstat.restapi.controller;

import java.util.Collections;
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

import com.serpstat.restapi.exception.KeywordNotFoundException;
import com.serpstat.restapi.exception.KeywordNotUniqueInSiteException;
import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.service.KeywordService;
import com.serpstat.restapi.service.SiteService;

@RestController
public class KeywordRestController {
	static final Logger logger = LoggerFactory.getLogger(KeywordRestController.class);

	@Autowired
	SiteService siteService;

	@Autowired
	KeywordService keywordService;

	@RequestMapping(value = "/site/{siteId}/keyword/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Keyword> getKeyword(@PathVariable("siteId") long siteId, @PathVariable("id") long id)
			throws KeywordNotFoundException {
		logger.info("Fetching Keyword with id {}", id);
		Keyword keyword = keywordService.findById(id);
		if (keyword == null) {
			logger.info("Keyword with id {} not found", id);
			throw new KeywordNotFoundException("Keyword with id not found");
		}

		return new ResponseEntity<Keyword>(keyword, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/keyword", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Keyword>> listKeyword(@PathVariable("siteId") long siteId) throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", siteId);
		Site site = siteService.findById(siteId);
		if (site == null) {
			logger.info("Site with id {} not found", siteId);
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Fetching Keyword with siteId {}", siteId);
		List<Keyword> keywords = keywordService.findAllBySiteId(siteId);

		return new ResponseEntity<List<Keyword>>(keywords, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/keyword/paginated", params = { "offset",
			"size" }, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Keyword>> listKeywordPaginated(@PathVariable("siteId") long siteId,
			@RequestParam("offset") int offset, @RequestParam("size") int size) throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", siteId);
		Site site = siteService.findById(siteId);
		if (site == null) {
			logger.info("Site with id {} not found", siteId);
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Fetching Keyword with siteId {}", siteId);
		List<Keyword> keywords = keywordService.findPagenatedBySiteId(siteId, offset, size);

		return new ResponseEntity<List<Keyword>>(keywords, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/keyword/count", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Map<String, String>> fetchTotalCount(@PathVariable("siteId") long siteId)
			throws SiteNotFoundException {
		logger.info("Fetching Site with id {}", siteId);
		Site site = siteService.findById(siteId);
		if (site == null) {
			logger.info("Site with id {} not found", siteId);
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Fetching Keyword with siteId {}", siteId);
		int totalCount = keywordService.findTotalCountBySiteId(siteId);
		Map<String, String> result = Collections.singletonMap("totalCount", Integer.toString(totalCount));
		return new ResponseEntity<Map<String, String>>(result, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/keyword", method = RequestMethod.POST)
	public ResponseEntity<Void> createKeyword(@PathVariable("siteId") long siteId, @RequestBody Keyword keyword,
			UriComponentsBuilder ucBuilder) throws SiteNotFoundException, KeywordNotUniqueInSiteException {
		logger.info("Fetching Site with id {}", keyword.getSiteId());
		Site site = siteService.findById(keyword.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", keyword.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.debug("Creating Keyword {}", keyword);
		if (!(keywordService.isKeywordInSiteUnique(keyword.getSiteId(), keyword.getKeyword()))) {
			logger.debug("A Keyword with siteId {} already exist", keyword.getSiteId());
			throw new KeywordNotUniqueInSiteException("The keyword given is already exist");
		}
		logger.debug("{}", keyword);
		keywordService.saveKeyword(keyword);

		Map<String, Long> pathIds = new HashMap<>();
		pathIds.put("siteId", keyword.getSiteId());
		pathIds.put("id", keyword.getId());

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/site/{siteId}/keyword/{id}").buildAndExpand(pathIds).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/site/{siteId}/keyword/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Keyword> updateKeyword(@PathVariable("siteId") long siteId, @PathVariable("id") long id,
			@RequestBody Keyword keyword) throws SiteNotFoundException, KeywordNotFoundException {
		logger.info("Fetching Site with id {}", keyword.getSiteId());
		Site site = siteService.findById(keyword.getSiteId());
		if (site == null) {
			logger.info("Site with id {} not found", keyword.getSiteId());
			throw new SiteNotFoundException("Site with id not found");
		}

		logger.info("Updating Keyword {}", keyword.getId());
		Keyword currentKeyword = keywordService.findById(keyword.getId());

		if (currentKeyword == null) {
			logger.info("Keyword with id {} not found", keyword.getId());
			throw new KeywordNotFoundException("Keyword with id not found");
		}

		keywordService.updateKeyword(keyword);
		Keyword entity = keywordService.findById(keyword.getId());
		return new ResponseEntity<Keyword>(entity, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{siteId}/keyword/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Keyword> deleteKeyword(@PathVariable("siteId") long siteId, @PathVariable("id") long id)
			throws KeywordNotFoundException {
		logger.info("Fetching & Deleting Keyword with id {}", id);
		Keyword keyword = keywordService.findById(id);
		if (keyword == null) {
			logger.info("Unable to delete. Keyword with id {} not found", id);
			throw new KeywordNotFoundException("Keyword with id not found");
		}

		keywordService.deleteById(id);
		return new ResponseEntity<Keyword>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ SiteNotFoundException.class, Exception.class })
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
