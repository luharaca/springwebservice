package com.app.tech.blogs.common.exception;

/**
 * This exception is thrown when there is a business error
 * 
 * @author Haochuan
 *
 */
public class BusinessException extends RuntimeException {

	private static final long serialVersionUID = -6469800069955282762L;

	public BusinessException() {
		super();
	}

	public BusinessException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
