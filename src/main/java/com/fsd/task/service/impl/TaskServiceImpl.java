package com.fsd.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fsd.task.dto.ParentTaskDTO;
import com.fsd.task.dto.TaskDTO;
import com.fsd.task.entity.ParentTask;
import com.fsd.task.entity.TaskEntity;
import com.fsd.task.entity.UserEntity;
import com.fsd.task.exception.TaskNotFoundException;
import com.fsd.task.repository.ParentTaskRepository;
import com.fsd.task.repository.TaskRepository;
import com.fsd.task.repository.UserRespository;
import com.fsd.task.service.TaskService;

@Service("taskService")
public class TaskServiceImpl implements TaskService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ParentTaskRepository parentTaskRepository;
	
	@Autowired
	UserRespository userRepository;
	
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
		task.setProjectId(taskDTO.getProjectId());
		task.setParent('N');
		if(taskDTO.isParentFlag()) {
			ParentTask parentTask = parentTaskRepository.findByParentTaskNameIgnoreCase(taskDTO.getParentTask());
			if (null == parentTask) {
				parentTask = new ParentTask();
			}
			parentTask.setParentTaskName(taskDTO.getTaskName());
			parentTaskRepository.save(parentTask);
			task.setParent('Y');
		}
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
		
		if(!StringUtils.isEmpty(taskDTO.getUser())) {
			UserEntity userEntity = userRepository.findByEmpIdIgnoreCase(taskDTO.getUser());
			if(null != userEntity) {
				userEntity.setTaskId(addedTask.getTaskId());
				userRepository.save(userEntity);
			}
		}
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
		taskDTO.setParentFlag('Y' == addedTask.getParent() ? true : false);
		if(null != addedTask.getProjectEntity()) {
			taskDTO.setProjectName(addedTask.getProjectEntity().getProjectName());
		}
		UserEntity userEntity = userRepository.findByTaskId(addedTask.getTaskId());
		if(null != userEntity) {
			taskDTO.setUser(userEntity.getEmpId());
		}
		return taskDTO;
	}
	
	private ParentTaskDTO convertToDto(ParentTask parentTask) {
		ModelMapper modelMapper = new ModelMapper();
		ParentTaskDTO parentTaskDTO = modelMapper.map(parentTask, ParentTaskDTO.class);
		return parentTaskDTO;
	}

	@Override
	public List<ParentTaskDTO> getAllParentTasks() {
		return parentTaskRepository.findAll().stream().map(parentTaskEntity -> convertToDto(parentTaskEntity)).collect(Collectors.toList());
	}

}
