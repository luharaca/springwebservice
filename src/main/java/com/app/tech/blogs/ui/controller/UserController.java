package com.app.tech.blogs.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.common.exception.BusinessException;
import com.app.tech.blogs.service.UserService;
import com.app.tech.blogs.ui.model.request.UserRequest;
import com.app.tech.blogs.ui.model.response.UserResponse;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;
	
	@GetMapping
	public String getUser() {
		return "getUser() was called";
	}
	
	@PostMapping
	public UserResponse createUser(@RequestBody UserRequest userInRequest) throws BusinessException {
		UserResponse userResponse = new UserResponse();
		
		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userInRequest, userDTO);
		
		UserDTO newUser = userService.createUser(userDTO);
		BeanUtils.copyProperties(newUser, userResponse);
		
		return userResponse;
	}
	
	@PutMapping
	public String updateUser() {
		return "updateUser() was called";
	}
	
	@DeleteMapping
	public String deleteUser() {
		return "deleteUser() was called";
	}
}
