package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "UserAPI Not Found")
public class UserAPINotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public UserAPINotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
