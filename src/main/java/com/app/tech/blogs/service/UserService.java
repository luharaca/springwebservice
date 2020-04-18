package com.app.tech.blogs.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;
import com.app.tech.blogs.common.exception.InternalServerException;

public interface UserService extends UserDetailsService {

	UserDTO createUser(UserDTO userDTO) throws BusinessException, InternalServerException;
}
