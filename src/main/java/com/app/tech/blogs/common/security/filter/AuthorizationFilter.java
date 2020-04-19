package com.app.tech.blogs.common.security.filter;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Jwts;

public class AuthorizationFilter extends BasicAuthenticationFilter {

	public AuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String authorizationHeader = request.getHeader(SecurityConstants.AUTHORIZATION_HEADER);

		if (authorizationHeader != null && authorizationHeader.startsWith(SecurityConstants.TOKEN_PREFIX)) {
			SecurityContextHolder.getContext().setAuthentication(getAuthentication(authorizationHeader));
		}

		chain.doFilter(request, response);
	}

	private Authentication getAuthentication(String authorizationHeader) {
		if (authorizationHeader != null) {
			String token = authorizationHeader.replace(SecurityConstants.TOKEN_PREFIX, "").trim();

			String username = Jwts.parser().setSigningKey(SecurityConstants.TOKEN_SECRET).parseClaimsJws(token)
					.getBody().getSubject();

			if (username != null) {
				return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>());
			}
		}

		return null;
	}

}
