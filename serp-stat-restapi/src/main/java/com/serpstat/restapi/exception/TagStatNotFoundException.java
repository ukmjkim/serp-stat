package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tag Stat Not Found")
public class TagStatNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagStatNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}