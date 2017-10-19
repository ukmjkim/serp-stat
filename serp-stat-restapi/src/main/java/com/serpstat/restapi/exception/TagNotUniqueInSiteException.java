package com.serpstat.restapi.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The tag given is already exist")
public class TagNotUniqueInSiteException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public TagNotUniqueInSiteException(String errorMessage) {
		super(errorMessage);
	}
}