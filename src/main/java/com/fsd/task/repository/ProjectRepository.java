package com.fsd.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.task.entity.ProjectEntity;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {
	List<ProjectEntity> findAll();
}
