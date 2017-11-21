package com.serpstat.rest.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serpstat.rest.domain.ExceptionInfo;
import com.serpstat.rest.domain.Keyword;
import com.serpstat.rest.exception.KeywordNotFoundException;
import com.serpstat.rest.exception.KeywordNotUniqueInSiteException;
import com.serpstat.rest.repository.KeywordRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class KeywordController {

	@Autowired
	KeywordRepository keywordRepository;
	
	@GetMapping("/site/{siteId}/keywords")
	public ResponseEntity<List<Keyword>> listAllKeywords() {
		List<Keyword> keywords = keywordRepository.findAll();
		if (keywords.isEmpty()) {
			return new ResponseEntity<List<Keyword>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Keyword>>(keywords, HttpStatus.OK);
	}
	
	@GetMapping("/site/{siteId}/keywords/{keywordId}")
	public ResponseEntity<Keyword> getKeyword(@PathVariable Long keywordId) {
		Keyword device = keywordRepository.findById(keywordId).orElse(null);
		if (device == null) {
			return new ResponseEntity<Keyword>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<Keyword>(device, HttpStatus.OK);		
	}
	
	@PostMapping("/site/{siteId}/keywords")
	public ResponseEntity<Void> createKeyword(@RequestBody Keyword keyword) throws KeywordNotUniqueInSiteException {
		Keyword entity = keywordRepository.findBySiteIdAndKeyword(keyword.getSiteId(), keyword.getKeyword()).orElse(null);
		if (entity != null) {
			log.info("The keyword given is already exist in the site([]) []", keyword.getSiteId(), keyword.getKeyword());
			throw new KeywordNotUniqueInSiteException("The keyword given is already exist in the site");
		}

		Keyword createdEntity = keywordRepository.saveAndFlush(keyword);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEntity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/site/{siteId}/keywords/{keywordId}")
	public ResponseEntity<Keyword> updateKeyword(@PathVariable Long keywordId, @RequestBody Keyword keyword) throws KeywordNotFoundException {
		Keyword entity = keywordRepository.findById(keyword.getId()).orElse(null);
		if (entity == null) {
			throw new KeywordNotFoundException("Device with id not found");
		}

		Keyword updatedEntity = keywordRepository.saveAndFlush(keyword);
		return new ResponseEntity<Keyword>(updatedEntity, HttpStatus.OK);
	}
	
	@DeleteMapping("/site/{siteId}/keywords/{keywordId}")
	public ResponseEntity<Void> deleteKeyword(@PathVariable Long keywordId) throws KeywordNotFoundException {
		Keyword entity = keywordRepository.findById(keywordId).orElse(null);
		if (entity == null) {
			throw new KeywordNotFoundException("Device with id not found");
		}

		keywordRepository.deleteById(keywordId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ KeywordNotFoundException.class, KeywordNotUniqueInSiteException.class })
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
