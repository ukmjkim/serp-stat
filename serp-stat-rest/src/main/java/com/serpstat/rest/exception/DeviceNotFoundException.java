package com.serpstat.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Device Not Found")
public class DeviceNotFoundException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public DeviceNotFoundException(String errorMessage) {
		super(errorMessage);
	}
}
