package com.app.tech.blogs.ui.model.response.operation;

public enum OperationalStatus {
	ERROR("The operation failed"), SUCCESS("The operation was successful");

	private String message;

	OperationalStatus(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}
