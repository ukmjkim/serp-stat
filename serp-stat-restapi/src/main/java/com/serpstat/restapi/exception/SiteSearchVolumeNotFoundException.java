package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Site Search Volume Not Found")
public class SiteSearchVolumeNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public SiteSearchVolumeNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}