package com.app.tech.blogs.service;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;

public interface UserService {
	
	UserDTO createUser(UserDTO userDTO) throws BusinessException;
}
