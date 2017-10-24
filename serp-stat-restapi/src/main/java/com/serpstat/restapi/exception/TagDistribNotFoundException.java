package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tag Distrib Not Found")
public class TagDistribNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagDistribNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}