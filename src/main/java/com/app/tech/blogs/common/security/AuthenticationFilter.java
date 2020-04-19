package com.app.tech.blogs.common.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.tech.blogs.SpringApplicationContext;
import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;
import com.app.tech.blogs.common.exception.InternalServerException;
import com.app.tech.blogs.service.UserService;
import com.app.tech.blogs.ui.model.request.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private ObjectMapper objectMapper;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		objectMapper = new ObjectMapper();
		this.setAuthenticationManager(authenticationManager);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {

		try {
			LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);

			return this.getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(
					loginRequest.getUsername(), loginRequest.getPassword(), new ArrayList<>()));

		} catch (Exception ex) {
			throw new BusinessException("The login request message is not valid " + ex.getMessage());
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		String username = ((User) authResult.getPrincipal()).getUsername();

		response.addHeader(SecurityConstants.HEADER, SecurityConstants.TOKEN_PREFIX + generateJSONWebToken(username));

		response.addHeader(SecurityConstants.USER_ID, getUserId(username));
	}

	private String generateJSONWebToken(String username) {
		return Jwts.builder().setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
				.signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET).compact();
	}

	private String getUserId(String username) {
		UserService userService = (UserService) SpringApplicationContext.getBean("userServiceImpl");
		UserDTO user = userService.findUserByUsername(username);

		if (user != null) {
			return user.getUserId();
		}

		throw new InternalServerException("User with username " + username + " cannot be found");
	}
}
