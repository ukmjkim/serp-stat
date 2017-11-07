package com.serpstat.rest.exception;

public abstract class AbstractException extends Exception {
	private static final long serialVersionUID = 1L;
	private String errorMessage;

	public String getErrorMessage() {
		return errorMessage;
	}

	public AbstractException(String errorMessage) {
		super(errorMessage);
		this.errorMessage = errorMessage;
	}

	public AbstractException() {
		super();
	}
}
