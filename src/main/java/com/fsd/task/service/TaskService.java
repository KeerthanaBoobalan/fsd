package com.fsd.task.service;

import java.util.List;

import com.fsd.task.dto.ParentTaskDTO;
import com.fsd.task.dto.TaskDTO;
import com.fsd.task.exception.TaskNotFoundException;

public interface TaskService {
	
	List<TaskDTO> getAllTasks();
	TaskDTO getTask(Long taskId) throws TaskNotFoundException;
	TaskDTO addTask(TaskDTO taskDTO);
	TaskDTO updateTask(TaskDTO taskDTO) throws TaskNotFoundException;
	String deleteTask(Long taskId) throws TaskNotFoundException;
	List<ParentTaskDTO> getAllParentTasks();
}
