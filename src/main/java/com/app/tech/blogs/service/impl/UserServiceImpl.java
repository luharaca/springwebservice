package com.app.tech.blogs.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;
import com.app.tech.blogs.io.entity.UserEntity;
import com.app.tech.blogs.repository.UserRepository;
import com.app.tech.blogs.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDTO createUser(UserDTO userDTO) throws BusinessException {
		
		if (userExists(userDTO)) {
			throw new BusinessException("Record already exist!");
		}
		
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
	
	private boolean userExists(UserDTO userDTO) {
		return userDTO != null && (userRepository.findByEmail(userDTO.getEmail())) != null;
	}

}
