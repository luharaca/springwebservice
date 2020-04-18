package com.app.tech.blogs.common.security;

public class SecurityConstants {

	private SecurityConstants() {
		// private constructor
	}

	public static final long EXPIRATION_TIME = 864000000; // 10 calendar days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER = "Authorization";
	public static final String SIGN_UP_URL = "/users";
	public static final String TOKEN_SECRET = "jf9i4jgu83nf10";
}
