package com.serpstat.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The region and lang given is already exist")
public class MarketRegionAndLangNotUniqueException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public MarketRegionAndLangNotUniqueException(String errorMessage) {
		super(errorMessage);
	}
}
