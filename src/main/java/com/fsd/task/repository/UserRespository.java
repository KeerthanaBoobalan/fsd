package com.fsd.task.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.fsd.task.entity.UserEntity;

@Repository
public interface UserRespository extends PagingAndSortingRepository<UserEntity, Long>  {
	
	List<UserEntity> findAll(Sort sort);
	UserEntity findByEmpIdIgnoreCase(String empId);
	UserEntity findByProjId(Long projId);

}
