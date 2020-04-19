package com.app.tech.blogs.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
	public UserDTO createUser(UserDTO userDTO) {

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

	@Override
	public UserDetails loadUserByUsername(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);

		if (userEntity != null) {
			return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
		}

		throw new UsernameNotFoundException("The user registered with email " + email + " could not be found.");
	}

	@Override
	public UserDTO findUserByUsername(String username) {
		UserDTO userDTO = new UserDTO();

		UserEntity userEntity = userRepository.findByEmail(username);
		BeanUtils.copyProperties(userEntity, userDTO);

		return userDTO;
	}

	@Override
	public UserDTO findUserByUserId(String userId) {
		UserDTO userDTO = new UserDTO();

		UserEntity userEntity = userRepository.findByUserId(userId);

		if (userEntity != null) {
			BeanUtils.copyProperties(userEntity, userDTO);

			return userDTO;
		}

		throw new BusinessException("User with userId " + userId + " could not be found");
	}

}
