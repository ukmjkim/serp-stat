package com.serpstat.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Keyword Not Found")
public class KeywordNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public KeywordNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
