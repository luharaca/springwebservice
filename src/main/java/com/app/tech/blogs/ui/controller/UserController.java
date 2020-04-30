package com.app.tech.blogs.ui.controller;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.cfg.beanvalidation.GroupsPerOperation.Operation;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.app.tech.blogs.common.dto.UserDTO;
import com.app.tech.blogs.service.UserService;
import com.app.tech.blogs.ui.model.request.UserRequest;
import com.app.tech.blogs.ui.model.response.OperationResponse;
import com.app.tech.blogs.ui.model.response.UserResponse;
import com.app.tech.blogs.ui.model.response.operation.OperationalStatus;

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
	
	
	@GetMapping(produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE })
	public List<UserResponse> getUsers(@RequestParam(defaultValue="1", required=false) int page, @RequestParam(defaultValue="10000",required=false) int limit) {
		List<UserResponse> userResponseList = new ArrayList<>();

		List<UserDTO> users = userService.findUsersByPage(page,limit);
		
		for (UserDTO userDTO: users) {
			UserResponse userResponse = new UserResponse();
			BeanUtils.copyProperties(userDTO, userResponse);
			userResponseList.add(userResponse);
		}

		return userResponseList;
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

	@DeleteMapping(path = "/{id}")
	public OperationResponse deleteUser(@PathVariable String id) {

		userService.deleteUserById(id);

		return buildOperationResponse(Operation.DELETE.getName(), OperationalStatus.SUCCESS.getMessage());
	}

	private OperationResponse buildOperationResponse(String operation, String message) {
		OperationResponse operationResponse = new OperationResponse();

		operationResponse.setOperation(operation);
		operationResponse.setOperationalStatus(message);

		return operationResponse;
	}
}
