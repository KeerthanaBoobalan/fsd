package com.fsd.task.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.task.entity.ParentTask;

public interface ParentTaskRepository extends JpaRepository<ParentTask, Long> {
	
	ParentTask findByParentTaskNameIgnoreCase(String parentTask);
	
	List<ParentTask> findAll();
}
