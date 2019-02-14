package com.fsd.task.service;

import java.util.List;

import com.fsd.task.dto.UserDTO;
import com.fsd.task.exception.UserNotFoundException;

public interface UserService {
	
	List<UserDTO> getAllUsers(String sort,String sortDirection);
	UserDTO addUser(UserDTO userDTO);
	UserDTO updateUser(UserDTO userDTO) throws UserNotFoundException;
	String deleteUser(Long userId) throws UserNotFoundException;

}
