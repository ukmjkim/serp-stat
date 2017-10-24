package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Site Distrib Not Found")
public class SiteDistribNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public SiteDistribNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}