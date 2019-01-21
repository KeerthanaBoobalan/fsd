package com.fsd.task.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fsd.task.entity.ParentTask;

public interface ParentTaskRepository extends JpaRepository<ParentTask, Long> {
	ParentTask findByParentTaskNameIgnoreCase(String parentTask);
}
