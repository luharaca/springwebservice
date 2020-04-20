package com.app.tech.blogs.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.service.UserService;
import com.app.tech.blogs.ui.model.request.UserRequest;
import com.app.tech.blogs.ui.model.response.UserResponse;

@RestController
@RequestMapping("users")
public class UserController {
	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse getUser(@PathVariable String id) {
		UserResponse userResponse = new UserResponse();

		UserDTO user = userService.findUserByUserId(id);
		BeanUtils.copyProperties(user, userResponse);

		return userResponse;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, produces = {
			MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse createUser(@RequestBody UserRequest userInRequest) {
		UserResponse userResponse = new UserResponse();

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userInRequest, userDTO);

		UserDTO newUser = userService.createUser(userDTO);
		BeanUtils.copyProperties(newUser, userResponse);

		return userResponse;
	}

	@PutMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public UserResponse updateUser(@PathVariable String id, @RequestBody UserRequest userInRequest) {
		UserResponse userResponse = new UserResponse();

		UserDTO userDTO = new UserDTO();
		BeanUtils.copyProperties(userInRequest, userDTO);

		UserDTO updatedUser = userService.updateUser(id, userDTO);
		BeanUtils.copyProperties(updatedUser, userResponse);

		return userResponse;
	}

	@DeleteMapping
	public String deleteUser() {
		return "deleteUser() was called";
	}
}
