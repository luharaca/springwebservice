package com.app.tech.blogs.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;
import com.app.tech.blogs.common.util.IdUtils;
import com.app.tech.blogs.io.entity.UserEntity;
import com.app.tech.blogs.repository.UserRepository;
import com.app.tech.blogs.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	IdUtils idUtils;

	@Override
	public UserDTO createUser(UserDTO userDTO) throws BusinessException {
		
		if (userExists(userDTO)) {
			throw new BusinessException("Record already exist!");
		}
		
		UserEntity userEntity = new UserEntity();
		
		if (userDTO != null) {
		   BeanUtils.copyProperties(userDTO, userEntity);
		}
		
		userEntity.setEncryptedPassword("test");
		userEntity.setUserId(idUtils.generateId(32));
		
		UserEntity savedUserEntity = userRepository.save(userEntity);
		
		UserDTO newUserDTO = new UserDTO();
		BeanUtils.copyProperties(savedUserEntity, newUserDTO);
		
		return newUserDTO;
	}
	
	private boolean userExists(UserDTO userDTO) {
		return userDTO != null && (userRepository.findByEmail(userDTO.getEmail())) != null;
	}

}
