package com.serpstat.restapi.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Keyword;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.SiteTags;
import com.serpstat.restapi.service.KeywordService;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.TagService;

@RestController
public class SiteRestController {
	static final Logger logger = LoggerFactory.getLogger(SiteRestController.class);

	private static String UPLOADED_FOLDER = "/tmp/";
	private static char DELIMITER = ',';

	@Autowired
	SiteService siteService;

	@Autowired
	TagService tagService;

	@Autowired
	KeywordService keywordService;

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
	public ResponseEntity<SiteTags> listAllTagsBySiteId(@PathVariable("id") long id) throws SiteNotFoundException {
		SiteTags siteTags = siteService.fetchTagsBySite(id);
		if (siteTags == null || siteTags.getId() == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteNotFoundException("Site with id not found");
		}
		return new ResponseEntity<SiteTags>(siteTags, HttpStatus.OK);
	}

	@RequestMapping(value = "/site/{id}/keyword/upload", method = RequestMethod.POST)
	public ResponseEntity<?> upload(@PathVariable("id") long id, @RequestParam("file") MultipartFile uploadFile)
			throws Exception {
		logger.info("Fetching Site with id {}", id);
		Site site = siteService.findById(id);
		if (site == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteNotFoundException("Site with id not found");
		}

		if (uploadFile.isEmpty()) {
			return new ResponseEntity<>("please select a file!", HttpStatus.OK);
		} else {
			try {
				String fileName = saveUploadedFile(uploadFile);
				logger.info("keyword file uploaded {}", fileName);
				storeUploadedKeyword(id, fileName);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			return new ResponseEntity<Object>(HttpStatus.OK);
		}
	}

	private String saveUploadedFile(MultipartFile file) throws IOException {
		byte[] bytes = file.getBytes();
		Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
		Files.write(path, bytes);
		return UPLOADED_FOLDER + file.getOriginalFilename();
	}

	private void storeUploadedKeyword(long siteId, String fileName) throws Exception {
		logger.info("storeUploadedKeyword {}", fileName);

		File file = new File(fileName);

		CsvMapper mapper = new CsvMapper();
		CsvSchema schema = CsvSchema.emptySchema().withHeader().withColumnSeparator(DELIMITER);

		try {
			MappingIterator<Keyword> mi = mapper.readerFor(Keyword.class).with(schema).readValues(file);
			while (mi.hasNext()) {
				Keyword keyword = mi.next();
				keyword.setSiteId(siteId);
				logger.info("keyword {}", keyword);
				if (!(keywordService.isKeywordInSiteUnique(keyword.getSiteId(), keyword.getKeyword()))) {
					logger.debug("A Keyword with siteId {} already exist", keyword.getSiteId());
				}
				keywordService.saveKeyword(keyword);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
