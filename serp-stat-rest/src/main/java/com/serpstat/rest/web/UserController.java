package com.serpstat.rest.web;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.serpstat.rest.domain.ExceptionInfo;
import com.serpstat.rest.domain.User;
import com.serpstat.rest.exception.UserLoginNotUniqueException;
import com.serpstat.rest.exception.UserNotFoundException;
import com.serpstat.rest.repository.UserRepository;

@RestController
public class UserController {
	static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/users/")
	public ResponseEntity<List<User>> listAllUsers() {
		List<User> users = userRepository.findAll();
		if (users.isEmpty()) {
			return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<User>>(users, HttpStatus.OK);
	}
	
	@GetMapping("/users/{userId}")
	public ResponseEntity<User> getUser(@PathVariable Long userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null) {
			return new ResponseEntity<User>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);		
	}
	
	@PostMapping("/users")
	public ResponseEntity<Void> createUser(@RequestBody User user) throws UserLoginNotUniqueException {
		User entity = userRepository.findByLogin(user.getLogin()).orElse(null);
		if (entity != null) {
			throw new UserLoginNotUniqueException("The login given is already exist");
		}

		User createdEntity = userRepository.saveAndFlush(user);
		
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdEntity.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) throws UserNotFoundException {
		User entity = userRepository.findById(user.getId()).orElse(null);
		if (entity == null) {
			throw new UserNotFoundException("User with id not found");
		}

		User updatedEntity = userRepository.saveAndFlush(user);
		return new ResponseEntity<User>(updatedEntity, HttpStatus.OK);
	}
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long userId) throws UserNotFoundException {
		User entity = userRepository.findById(userId).orElse(null);
		if (entity == null) {
			throw new UserNotFoundException("User with id not found");
		}

		userRepository.deleteById(userId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler({ UserNotFoundException.class, UserLoginNotUniqueException.class })
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
