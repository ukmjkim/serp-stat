package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The site search volume given is already exist")
public class SiteSearchVolumeNotUniqueInSiteException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public SiteSearchVolumeNotUniqueInSiteException(String errorMessage) {
		super(errorMessage);
	}
}