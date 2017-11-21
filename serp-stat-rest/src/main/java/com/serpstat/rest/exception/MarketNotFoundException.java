package com.serpstat.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Market Not Found")
public class MarketNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public MarketNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
