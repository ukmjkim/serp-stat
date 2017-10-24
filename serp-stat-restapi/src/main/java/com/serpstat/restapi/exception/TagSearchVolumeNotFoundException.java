package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Tag Search Volume Not Found")
public class TagSearchVolumeNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagSearchVolumeNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}