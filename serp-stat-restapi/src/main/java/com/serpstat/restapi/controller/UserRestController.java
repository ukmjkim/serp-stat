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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.serpstat.restapi.exception.SiteNotFoundException;
import com.serpstat.restapi.exception.SiteTitleNotUniqueException;
import com.serpstat.restapi.exception.UserAPINotFoundException;
import com.serpstat.restapi.exception.UserLoginNotUniqueException;
import com.serpstat.restapi.exception.UserNotFoundException;
import com.serpstat.restapi.model.ExceptionInfo;
import com.serpstat.restapi.model.Site;
import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.UserAPI;
import com.serpstat.restapi.service.SiteService;
import com.serpstat.restapi.service.UserAPIService;
import com.serpstat.restapi.service.UserService;

@RestController
public class UserRestController {
	static final Logger logger = LoggerFactory.getLogger(UserRestController.class);

	@Autowired
	UserService userService;

	@Autowired
	UserAPIService userApiService;

	@Autowired
	SiteService siteService;

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) throws UserNotFoundException {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.info("User with id {} not found", id);
			throw new UserNotFoundException("User with id not found");
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder)
			throws UserLoginNotUniqueException {
		logger.debug("Creating User {}", user.getLogin());
		if (!(userService.isUserLoginUnique(user.getId(), user.getLogin()))) {
			logger.debug("A User with login {} already exist", user.getLogin());
			throw new UserLoginNotUniqueException("The login given is already exist");
		}
		logger.debug("{}", user);
		userService.saveUser(user);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user)
			throws UserNotFoundException {
		logger.info("Updating User {}", id);
		User currentUser = userService.findById(id);

		if (currentUser == null) {
			logger.info("User with id {} not found", id);
			throw new UserNotFoundException("User with id not found");
		}

		userService.updateUser(user);
		return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) throws UserNotFoundException {
		logger.info("Fetching & Deleting User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.info("Unable to delete. User with id {} not found", id);
			throw new UserNotFoundException("User with id not found");
		}

		userService.deleteByLogin(user.getLogin());
		return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/user/{userId}/userapi", method = RequestMethod.POST)
	public ResponseEntity<Void> addUserAPI(@PathVariable("userId") long userId, @RequestBody UserAPI userApi,
			UriComponentsBuilder ucBuilder) throws UserNotFoundException {
		logger.info("Adding User API on this User {}", userId);
		User currentUser = userService.findById(userId);

		if (currentUser == null) {
			logger.info("User with id {} not found", userId);
			throw new UserNotFoundException("User with id not found");
		}
		userApi.setUser(currentUser);
		userApiService.saveUserAPI(userApi);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(
				ucBuilder.path("/user/{userId}/userapi/{id}").buildAndExpand(userId, userApi.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/{userId}/userapi/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserAPI> updateUserAPI(@PathVariable("userId") long userId, @PathVariable("id") int id,
			@RequestBody UserAPI userApi) throws UserAPINotFoundException {
		logger.info("Updating User API {}", id);
		UserAPI currentUserApi = userApiService.findById(id);

		if (currentUserApi == null) {
			logger.info("User with id {} not found", id);
			throw new UserAPINotFoundException("UserAPI with id not found");
		}

		userApiService.updateUserAPI(userApi);
		return new ResponseEntity<UserAPI>(currentUserApi, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{userId}/userapi/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserAPI> deleteUserAPI(@PathVariable("userId") long userId, @PathVariable("id") int id)
			throws UserAPINotFoundException {
		logger.info("Fetching & Deleting User API with id {}", id);
		UserAPI userApi = userApiService.findById(id);
		if (userApi == null) {
			logger.info("Unable to delete. UserAPI with id {} not found", id);
			throw new UserAPINotFoundException("UserAPI with id not found");
		}

		userApiService.deleteUserAPI(userApi);
		return new ResponseEntity<UserAPI>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/user/{userId}/site", method = RequestMethod.POST)
	public ResponseEntity<Void> addSite(@PathVariable("userId") long userId, @RequestBody Site site,
			UriComponentsBuilder ucBuilder) throws Exception {
		logger.debug("Creating Site {}", site.getTitle());
		User currentUser = userService.findById(userId);
		if (currentUser == null) {
			logger.info("User with id {} not found", userId);
			throw new UserNotFoundException("User with id not found");
		}

		if (!(siteService.isSiteTitleUnique(site.getId(), userId, site.getTitle()))) {
			logger.debug("A Site with title {} already exist", site.getTitle());
			throw new SiteTitleNotUniqueException("The title given is already exist");
		}
		logger.debug("{}", site);
		site.setUser(currentUser);
		siteService.saveSite(site);

		HttpHeaders headers = new HttpHeaders();
		headers.setLocation(ucBuilder.path("/user/{userId}/userapi/{id}").buildAndExpand(userId, site.getId()).toUri());
		return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/{userId}/site/{id}", method = RequestMethod.PUT)
	public ResponseEntity<Site> updateSite(@PathVariable("userId") long userId, @PathVariable("id") long id,
			@RequestBody Site site) throws SiteNotFoundException {
		logger.info("Updating Site {}", id);
		Site currentSite = siteService.findById(id);

		if (currentSite == null) {
			logger.info("Site with id {} not found", id);
			throw new SiteNotFoundException("Site Not Found");
		}

		siteService.updateSite(site);
		return new ResponseEntity<Site>(currentSite, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{userId}/site/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<Site> deleteSite(@PathVariable("userId") long userId, @PathVariable("id") long id)
			throws SiteNotFoundException {
		logger.info("Fetching & Deleting Site with id {}", id);
		Site site = siteService.findById(id);
		if (site == null) {
			logger.info("Unable to delete. Site with id {} not found", id);
			throw new SiteNotFoundException("Site Not Found");
		}

		siteService.deleteById(id);
		return new ResponseEntity<Site>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ UserNotFoundException.class, UserLoginNotUniqueException.class, UserAPINotFoundException.class,
			SiteNotFoundException.class, SiteTitleNotUniqueException.class })
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
