package com.app.tech.blogs.common.exception;

public enum ExceptionMessage {

	USER_NOT_FOUND("User could not be found"), USER_ALREADY_EXISTS("User already exists");

	private String errorMessage;

	ExceptionMessage(String message) {
		errorMessage = message;
	}

	public String getExceptionMessage() {
		return errorMessage;
	}

}
