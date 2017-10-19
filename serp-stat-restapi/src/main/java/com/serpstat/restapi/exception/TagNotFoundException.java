package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tag Not Found")
public class TagNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}