package com.serpstat.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The keyword given is already exist in the site")
public class KeywordNotUniqueInSiteException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public KeywordNotUniqueInSiteException(String errorMessage) {
		super(errorMessage);
	}
}
