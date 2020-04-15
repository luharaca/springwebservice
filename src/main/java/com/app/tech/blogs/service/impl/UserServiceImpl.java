package com.app.tech.blogs.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.tech.blogs.common.UserDTO;
import com.app.tech.blogs.io.entity.UserEntity;
import com.app.tech.blogs.repository.UserRepository;
import com.app.tech.blogs.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userDTO, userEntity);
		
		// set fields that do not exist in UserDTO
		userEntity.setEncryptedPassword("test");
		userEntity.setUserId("test_user_id");
		
		UserEntity savedUserEntity = userRepository.save(userEntity);
		
		UserDTO newUserDTO = new UserDTO();
		BeanUtils.copyProperties(savedUserEntity, newUserDTO);
		
		return newUserDTO;
	}

}
