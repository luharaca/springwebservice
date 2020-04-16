package com.app.tech.blogs.common.util;

import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class IdUtils {
	
	private static final Random RANDOM = new Random();
    private static final String ALPHABET="1234567890ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopurstuvwxyz";
    
    public String generateId(int length) {
    	StringBuilder sb = new StringBuilder();
    	
    	for (int i = 0; i < length; i++) {
    		sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
    	}
    	
    	return sb.toString();
    }
}
