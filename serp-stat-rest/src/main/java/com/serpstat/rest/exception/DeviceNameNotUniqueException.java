package com.serpstat.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "The device name given is already exist")
public class DeviceNameNotUniqueException extends AbstractException {
	private static final long serialVersionUID = 1L;

	public DeviceNameNotUniqueException(String errorMessage) {
		super(errorMessage);
	}
}
