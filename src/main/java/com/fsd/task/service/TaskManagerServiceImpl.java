package com.fsd.task.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fsd.task.dto.TaskDTO;
import com.fsd.task.entity.ParentTask;
import com.fsd.task.entity.TaskEntity;
import com.fsd.task.exception.TaskNotFoundException;
import com.fsd.task.repository.ParentTaskRepository;
import com.fsd.task.repository.TaskRepository;

@Service("taskManagerService")
public class TaskManagerServiceImpl implements TaskManagerService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ParentTaskRepository parentTaskRepository;
	
	public List<TaskDTO >getAllTasks() {
		return taskRepository.findAll().stream().map(taskEntity -> convertToDto(taskEntity)).collect(Collectors.toList());
	}

	public TaskDTO getTask(Long taskId) throws TaskNotFoundException {
		TaskEntity task = getTaskById(taskId);
		return convertToDto(task);
	}

	private TaskEntity getTaskById(Long taskId) throws TaskNotFoundException {
		TaskEntity task = taskRepository.findByTaskId(taskId).orElseThrow(() -> new TaskNotFoundException("Task not found for Particular Task Id"));
		return task;
	}

	public TaskDTO addTask(TaskDTO taskDTO) {
		TaskEntity task = new TaskEntity();
		return populateTask(taskDTO, task);
	}

	private TaskDTO populateTask(TaskDTO taskDTO, TaskEntity task) {
		task.setTaskName(taskDTO.getTaskName());
		task.setStartDate(taskDTO.getStartDate());
		task.setEndDate(taskDTO.getEndDate());
		task.setPriority(taskDTO.getPriority());
		if (!StringUtils.isEmpty(taskDTO.getParentTask())) {
			ParentTask parentTask = parentTaskRepository.findByParentTaskNameIgnoreCase(taskDTO.getParentTask());
			if (null != parentTask) {
				task.setParentTask(parentTask);
			} else {
				parentTask = new ParentTask();
				parentTask.setParentTaskName(taskDTO.getParentTask());
				task.setParentTask(parentTask);
			}
		}
		TaskEntity addedTask = taskRepository.save(task);
		return convertToDto(addedTask);
	}
	
	public TaskDTO updateTask(TaskDTO taskDTO) throws TaskNotFoundException {
		TaskEntity task = getTaskById(taskDTO.getTaskId());
		return populateTask(taskDTO, task);
	}

	public String deleteTask(Long taskId) throws TaskNotFoundException {
		TaskEntity task = getTaskById(taskId);
		taskRepository.delete(task);
		return "Task deleted Successfully";
	}
	
	private TaskDTO convertToDto(TaskEntity addedTask) {
		ModelMapper modelMapper = new ModelMapper();
		TaskDTO taskDTO = modelMapper.map(addedTask, TaskDTO.class);
		return taskDTO;
	}
}
