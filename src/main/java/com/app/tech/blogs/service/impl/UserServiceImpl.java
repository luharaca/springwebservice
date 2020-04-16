package com.app.tech.blogs.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;
import com.app.tech.blogs.common.exception.InternalServerException;
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

	@Autowired
	BCryptPasswordEncoder bcryptPasswordEncoder;

	@Override
	public UserDTO createUser(UserDTO userDTO) throws BusinessException, InternalServerException {

		if (userDTO != null) {
			if (userExists(userDTO)) {
				throw new BusinessException("Record already exist!");
			}

			UserEntity userEntity = new UserEntity();

			BeanUtils.copyProperties(userDTO, userEntity);

			userEntity.setEncryptedPassword(bcryptPasswordEncoder.encode(userDTO.getPassword()));
			userEntity.setUserId(idUtils.generateId(32));

			UserEntity savedUserEntity = userRepository.save(userEntity);

			UserDTO newUserDTO = new UserDTO();
			BeanUtils.copyProperties(savedUserEntity, newUserDTO);

			return newUserDTO;
		}

		throw new InternalServerException("Unexpected error: userDTO is null");
	}

	private boolean userExists(UserDTO userDTO) {
		return (userRepository.findByEmail(userDTO.getEmail())) != null;
	}

}
