package com.app.tech.blogs.common.exception;

import java.util.Date;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.app.tech.blogs.ui.model.response.ErrorResponse;

@ControllerAdvice
public class ExceptionMapper {

	@ExceptionHandler(value = BusinessException.class)
	public ResponseEntity<Object> handleBusinessException(BusinessException ex) {
		return new ResponseEntity<>(buildErrorResponse(ex), new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ResponseEntity<Object> handleNotFoundException(UsernameNotFoundException ex) {
		return new ResponseEntity<>(buildErrorResponse(ex), new HttpHeaders(), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = { InternalServerException.class, Exception.class })
	public ResponseEntity<Object> handleException(Exception ex) {
		return new ResponseEntity<>(buildErrorResponse(ex), new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponse buildErrorResponse(Exception ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setTimeStamp(new Date());
		errorResponse.setMessage(ex.getMessage());
		return errorResponse;
	}
}
