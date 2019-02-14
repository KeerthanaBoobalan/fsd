package com.fsd.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fsd.task.dto.UserDTO;
import com.fsd.task.entity.UserEntity;
import com.fsd.task.exception.UserNotFoundException;
import com.fsd.task.repository.UserRespository;
import com.fsd.task.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	UserRespository userRepository;
	
	@Override
	public List<UserDTO> getAllUsers(String sort,String sortDirection) {
		return userRepository.findAll(getSort(sort,sortDirection)).stream().map(userEntity -> convertToUserDto(userEntity)).collect(Collectors.toList());
	}
	
	private Sort getSort(String sort,String sortDirection) {
		if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(sortDirection)) {
			if("DESC".equalsIgnoreCase(sortDirection))
				return new Sort(Sort.Direction.DESC,sort);
			else
				return new Sort(Sort.Direction.ASC,sort);
		}
		return new Sort(Sort.Direction.ASC,"empId");
	}

	private UserDTO convertToUserDto(UserEntity addedUser) {
		ModelMapper modelMapper = new ModelMapper();
		UserDTO userDTO = modelMapper.map(addedUser, UserDTO.class);
		return userDTO;
	}

	private UserDTO populateUser(UserDTO userDTO, UserEntity user) {
		user.setEmpId(userDTO.getEmpId());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		
		UserEntity addedUser = userRepository.save(user);
		return convertToUserDto(addedUser);
	}

	@Override
	public UserDTO addUser(UserDTO userDTO) {
		UserEntity user = new UserEntity();
		return populateUser(userDTO, user);
	}

	@Override
	public String deleteUser(Long userId) throws UserNotFoundException{
		UserEntity task = getUserById(userId);
		userRepository.delete(task);
		return "User deleted Successfully";
	}

	private UserEntity getUserById(Long userId) throws UserNotFoundException {
		UserEntity userEntity = userRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User Not Found for particular user id"));
		return userEntity;
	}

	@Override
	public UserDTO updateUser(UserDTO userDTO) throws UserNotFoundException {
		UserEntity user = getUserById(userDTO.getUserId());
		return populateUser(userDTO, user);
	}

}
