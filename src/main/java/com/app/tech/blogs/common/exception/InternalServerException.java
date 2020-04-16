package com.app.tech.blogs.common.exception;

public class InternalServerException extends Exception {

	private static final long serialVersionUID = 5702021195370294782L;

	public InternalServerException() {
		super();
	}

	public InternalServerException(String message) {
		super(message);
	}
}
