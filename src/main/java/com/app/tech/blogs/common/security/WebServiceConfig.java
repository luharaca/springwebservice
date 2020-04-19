package com.app.tech.blogs.common.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.app.tech.blogs.common.security.filter.AuthenticationFilter;
import com.app.tech.blogs.common.security.filter.AuthorizationFilter;
import com.app.tech.blogs.common.security.filter.SecurityConstants;
import com.app.tech.blogs.service.UserService;

@Configuration
@EnableWebSecurity
public class WebServiceConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	private BCryptPasswordEncoder bcryptPasswordEncoder;

	public WebServiceConfig() {
		bcryptPasswordEncoder = new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests().antMatchers(HttpMethod.POST, SecurityConstants.SIGN_UP_URL)
				.permitAll().anyRequest().authenticated().and().addFilter(getAuthenticationFilter())
				.addFilter(new AuthorizationFilter(this.authenticationManager()));
	}

	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(bcryptPasswordEncoder);
	}

	private AuthenticationFilter getAuthenticationFilter() throws Exception {
		AuthenticationFilter authenticationFilter = new AuthenticationFilter(this.authenticationManager());
		authenticationFilter.setFilterProcessesUrl("/users/login");
		return authenticationFilter;
	}
}
