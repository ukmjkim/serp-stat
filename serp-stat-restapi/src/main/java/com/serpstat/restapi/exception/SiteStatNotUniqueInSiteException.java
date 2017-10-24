package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The site stat given is already exist")
public class SiteStatNotUniqueInSiteException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public SiteStatNotUniqueInSiteException(String errorMessage) {
		super(errorMessage);
	}
}