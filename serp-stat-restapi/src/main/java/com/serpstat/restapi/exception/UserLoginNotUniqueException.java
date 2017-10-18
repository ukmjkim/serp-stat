package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The login given is already exist")
public class UserLoginNotUniqueException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public UserLoginNotUniqueException(String errorMessage) {
		super(errorMessage);
	}
}
