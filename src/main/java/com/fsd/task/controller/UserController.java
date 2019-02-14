package com.fsd.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.task.dto.UserDTO;
import com.fsd.task.exception.UserNotFoundException;
import com.fsd.task.service.UserService;

@RestController
@RequestMapping("/")
public class UserController {

	@Autowired
	UserService userService;
	
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "sortDirection", required = false) String sortDirection) {
		return new ResponseEntity<List<UserDTO>>(userService.getAllUsers(sort,sortDirection), HttpStatus.OK);
	}
	
	@PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(userService.addUser(userDTO), HttpStatus.OK);
	}
	
	@PutMapping(value = "/user/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO) throws UserNotFoundException {
		return new ResponseEntity<UserDTO>(userService.updateUser(userDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/user/{id}")
	@CrossOrigin
	public ResponseEntity<String> deleteUser(@PathVariable("id") long id) throws UserNotFoundException {
		return new ResponseEntity<String>(userService.deleteUser(id), HttpStatus.OK);
	}
}
