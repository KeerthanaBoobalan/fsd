package com.fsd.task.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fsd.task.dto.ProjectDTO;
import com.fsd.task.dto.TaskDTO;
import com.fsd.task.dto.UserDTO;
import com.fsd.task.entity.ParentTask;
import com.fsd.task.entity.ProjectEntity;
import com.fsd.task.entity.TaskEntity;
import com.fsd.task.entity.UserEntity;
import com.fsd.task.exception.TaskNotFoundException;
import com.fsd.task.repository.ParentTaskRepository;
import com.fsd.task.repository.ProjectRepository;
import com.fsd.task.repository.TaskRepository;
import com.fsd.task.repository.UserRespository;

@Service("taskManagerService")
public class TaskManagerServiceImpl implements TaskManagerService {

	@Autowired
	TaskRepository taskRepository;

	@Autowired
	ParentTaskRepository parentTaskRepository;
	
	@Autowired
	UserRespository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;
	
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
	public List<ProjectDTO> getAllProjects() {
		return projectRepository.findAll().stream().map(projectEntity -> convertToProjectDto(projectEntity)).collect(Collectors.toList());
	}

	@Override
	public ProjectDTO addProject(ProjectDTO projectDTO) {
		ProjectEntity project = new ProjectEntity();
		return populateProject(projectDTO, project);
	}
	private ProjectDTO convertToProjectDto(ProjectEntity addedProject) {
		ModelMapper modelMapper = new ModelMapper();
		ProjectDTO projectDTO = modelMapper.map(addedProject, ProjectDTO.class);
		UserEntity userEntity = userRepository.findByProjId(projectDTO.getProjectId());
		if(null != userEntity) {
			projectDTO.setManager(userEntity.getEmpId());
		}
		return projectDTO;
	}

	private ProjectDTO populateProject(ProjectDTO projectDTO, ProjectEntity project) {
		project.setProjectName(projectDTO.getProjectName());
		project.setPriority(projectDTO.getPriority());
		project.setStartDate(projectDTO.getStartDate());
		project.setEndDate(projectDTO.getEndDate());
		
		ProjectEntity addedProject = projectRepository.save(project);
		
		if(!StringUtils.isEmpty(projectDTO.getManager())) {
			UserEntity userEntity = userRepository.findByEmpIdIgnoreCase(projectDTO.getManager());
			if(null != userEntity) {
				userEntity.setProjId(project.getProjectId());
				userRepository.save(userEntity);
			}
		}
		return convertToProjectDto(addedProject);
	}
}
