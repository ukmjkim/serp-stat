package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Site Not Found")
public class SiteNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public SiteNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
