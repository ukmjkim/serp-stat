package com.serpstat.restapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The tag distrib given is already exist")
public class TagDistribNotUniqueInTagException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagDistribNotUniqueInTagException(String errorMessage) {
		super(errorMessage);
	}
}