package com.app.tech.blogs.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.tech.blogs.common.dto.UserDTO;

public interface UserService extends UserDetailsService {

	UserDTO createUser(UserDTO userDTO);

	UserDTO findUserByUsername(String username);
}
