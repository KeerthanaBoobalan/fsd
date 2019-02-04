package com.fsd.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.task.dto.ProjectDTO;
import com.fsd.task.dto.TaskDTO;
import com.fsd.task.exception.TaskNotFoundException;
import com.fsd.task.service.TaskManagerService;
import com.fsd.task.dto.UserDTO;
import com.fsd.task.exception.UserNotFoundException;

@RestController
@RequestMapping("/")
public class TaskManagerController {

	@Autowired
	TaskManagerService taskManagerService;
	
	@GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<List<TaskDTO>> getAllTasks() {
		return new ResponseEntity<List<TaskDTO>>(taskManagerService.getAllTasks(), HttpStatus.OK);
	}

	@GetMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") long id) throws TaskNotFoundException {
		return new ResponseEntity<TaskDTO>(taskManagerService.getTask(id), HttpStatus.OK);
	}

	@PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO) {
		return new ResponseEntity<TaskDTO>(taskManagerService.addTask(taskDTO), HttpStatus.OK);
	}
	
	@PutMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) throws TaskNotFoundException {
		return new ResponseEntity<TaskDTO>(taskManagerService.updateTask(taskDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/task/{id}")
	@CrossOrigin
	public ResponseEntity<String> deleteTask(@PathVariable("id") long id) throws TaskNotFoundException {
		return new ResponseEntity<String>(taskManagerService.deleteTask(id), HttpStatus.OK);
	}
	
	@GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "sortDirection", required = false) String sortDirection) {
		return new ResponseEntity<List<UserDTO>>(taskManagerService.getAllUsers(sort,sortDirection), HttpStatus.OK);
	}
	@PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<UserDTO> addUser(@RequestBody UserDTO userDTO) {
		return new ResponseEntity<UserDTO>(taskManagerService.addUser(userDTO), HttpStatus.OK);
	}
	@GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<List<ProjectDTO>> getAllProjects() {
		return new ResponseEntity<List<ProjectDTO>>(taskManagerService.getAllProjects(), HttpStatus.OK);
	}
	@PostMapping(value = "/project", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) {
		return new ResponseEntity<ProjectDTO>(taskManagerService.addProject(projectDTO), HttpStatus.OK);
	}
}
