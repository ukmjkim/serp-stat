package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The tag search volume given is already exist")
public class TagSearchVolumeNotUniqueInTagException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagSearchVolumeNotUniqueInTagException(String errorMessage) {
		super(errorMessage);
	}
}