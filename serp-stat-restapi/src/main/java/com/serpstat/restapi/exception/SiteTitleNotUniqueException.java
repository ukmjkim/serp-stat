package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="The title given is already exist")
public class SiteTitleNotUniqueException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public SiteTitleNotUniqueException(String errorMessage) {
		super(errorMessage);
	}
}