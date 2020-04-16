package com.app.tech.blogs.common.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class IdUtils {

	private static final SecureRandom RANDOM = new SecureRandom();
	private static final String ALPHABET = "1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopurstuvwxyz";

	public String generateId(int length) {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < length; i++) {
			sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
		}

		return sb.toString();
	}
}
