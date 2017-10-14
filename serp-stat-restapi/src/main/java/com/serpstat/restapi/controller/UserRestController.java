package com.serpstat.restapi.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.serpstat.restapi.model.User;
import com.serpstat.restapi.model.UserAPI;
import com.serpstat.restapi.service.UserAPIService;
import com.serpstat.restapi.service.UserService;

@RestController
public class UserRestController {
	static final Logger logger = LoggerFactory.getLogger(UserRestController.class);
	
	@Autowired
	UserService userService;

	@Autowired
	UserAPIService userApiService;

	@RequestMapping(value = "/user/", method = RequestMethod.GET)
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userService.findAllUsers();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(@PathVariable("id") long id) {
		logger.info("Fetching User with id {}", id);
		User user = userService.findById(id);
		if (user == null) {
			logger.info("User with id {} not found", id);
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/", method = RequestMethod.POST)
	public ResponseEntity<Void> createUser(@RequestBody User user, UriComponentsBuilder ucBuilder) {
		logger.debug("Creating User {}", user.getLogin());
		if (userService.isUserLoginUnique(user.getId(), user.getLogin())) {
			logger.debug("A User with login {} already exist", user.getLogin());
			return new ResponseEntity<Void>(HttpStatus.CONFLICT);
		}
        logger.debug("{}", user);
        userService.saveUser(user);
 
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.PUT)
	public ResponseEntity<User> updateUser(@PathVariable("id") long id, @RequestBody User user) {
		logger.info("Updating User {}", id);
		User currentUser = userService.findById(id);
        
        if (currentUser==null) {
        		logger.info("User with id {} not found", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
         
        userService.updateUser(user);
        return new ResponseEntity<User>(currentUser, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<User> deleteUser(@PathVariable("id") long id) {
		logger.info("Fetching & Deleting User with id {}", id);
		User user = userService.findById(id);
        if (user == null) {
        		logger.info("Unable to delete. User with id {} not found", id);
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
 
        userService.deleteByLogin(user.getLogin());
        return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(value = "/user/{userId}/userapi", method = RequestMethod.POST)
	public ResponseEntity<Void> addUserAPI(@PathVariable("userId") long userId, @RequestBody UserAPI userApi, UriComponentsBuilder ucBuilder) {
		logger.info("Adding User API on this User {}", userId);
		User currentUser = userService.findById(userId);
        
        if (currentUser==null) {
        		logger.info("User with id {} not found", userId);
            return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
        }
        
        userApiService.saveUserAPI(userApi);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(ucBuilder.path("/user/{userId}/userapi/{id}")
        		.buildAndExpand(userApi.getUser().getId(), userApi.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
	}


	@RequestMapping(value = "/user/{userId}/userapi/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserAPI> updateUserAPI(@PathVariable("userId") long userId, @PathVariable("id") int id, @RequestBody UserAPI userApi) {
		logger.info("Updating User API {}", id);
		UserAPI currentUserApi = userApiService.findById(id);
        
        if (currentUserApi==null) {
        		logger.info("User with id {} not found", id);
            return new ResponseEntity<UserAPI>(HttpStatus.NOT_FOUND);
        }
         
        userApiService.updateUserAPI(userApi);
        return new ResponseEntity<UserAPI>(currentUserApi, HttpStatus.OK);
	}

	@RequestMapping(value = "/user/{userId}/userapi/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<UserAPI> deleteUserAPI(@PathVariable("userId") long userId, @PathVariable("id") int id) {
		logger.info("Fetching & Deleting User API with id {}", id);
		UserAPI userApi = userApiService.findById(id);
        if (userApi == null) {
        		logger.info("Unable to delete. UserAPI with id {} not found", id);
            return new ResponseEntity<UserAPI>(HttpStatus.NOT_FOUND);
        }
 
        userApiService.deleteUserAPI(userApi);
        return new ResponseEntity<UserAPI>(HttpStatus.NO_CONTENT);
	}

}
