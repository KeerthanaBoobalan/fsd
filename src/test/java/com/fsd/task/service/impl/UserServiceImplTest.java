package com.fsd.task.service.impl;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;


import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Sort;

import com.fsd.task.entity.UserEntity;
import com.fsd.task.repository.UserRespository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
	
	@InjectMocks
	UserServiceImpl userServiceImpl;
	
	@Mock
	UserRespository userRepository;

	@Test
	public void getAllUsers_Test() {
		when(userRepository.findAll((Sort)any())).thenReturn(getUserList());
		userServiceImpl.getAllUsers("firstName", "ASC");
	}

	private List<UserEntity> getUserList() {
		List<UserEntity> userList = new ArrayList<>();
		return userList;
	}

}
