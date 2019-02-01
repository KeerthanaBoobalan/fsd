package com.fsd.task.repository;

import java.util.List;

import com.fsd.task.entity.UserEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRespository extends JpaRepository<UserEntity, Long>  {
	List<UserEntity> findAll();
}
