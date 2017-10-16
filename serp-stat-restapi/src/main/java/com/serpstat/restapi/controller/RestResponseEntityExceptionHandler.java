package com.serpstat.restapi.controller;

import java.io.IOException;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.serpstat.restapi.model.ExceptionInfo;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {
	private static final Logger logger = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ExceptionInfo> handleSQLException(RuntimeException ex, WebRequest request){
		logger.info("SQLException Occured:: URL="+request.getContextPath());
		ExceptionInfo error = new ExceptionInfo();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
	}
	
	@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="IOException occured")
	@ExceptionHandler(IOException.class)
	public ResponseEntity<ExceptionInfo> handleIOException(RuntimeException ex, WebRequest request){
		logger.error("IOException handler executed");
		ExceptionInfo error = new ExceptionInfo();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
	}

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<ExceptionInfo> handleConflict(RuntimeException ex, WebRequest request) {
		ExceptionInfo error = new ExceptionInfo();
		error.setErrorCode(HttpStatus.PRECONDITION_FAILED.value());
		error.setMessage(ex.getMessage());
		return new ResponseEntity<ExceptionInfo>(error, HttpStatus.OK);
    }
}
