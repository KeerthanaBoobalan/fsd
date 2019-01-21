package com.fsd.task.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.task.dto.TaskDTO;
import com.fsd.task.exception.TaskNotFoundException;
import com.fsd.task.service.TaskManagerService;

@RestController
@RequestMapping("/")
public class TaskManagerController {

	@Autowired
	TaskManagerService taskManagerService;
	
	@GetMapping(value = "/tasks", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TaskDTO>> getAllTasks() {
		return new ResponseEntity<List<TaskDTO>>(taskManagerService.getAllTasks(), HttpStatus.OK);
	}

	@GetMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskDTO> getTaskById(@PathVariable("id") long id) throws TaskNotFoundException {
		return new ResponseEntity<TaskDTO>(taskManagerService.getTask(id), HttpStatus.OK);
	}

	@PostMapping(value = "/task", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskDTO> addTask(@RequestBody TaskDTO taskDTO) {
		return new ResponseEntity<TaskDTO>(taskManagerService.addTask(taskDTO), HttpStatus.OK);
	}
	
	@PutMapping(value = "/task/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<TaskDTO> updateTask(@RequestBody TaskDTO taskDTO) throws TaskNotFoundException {
		return new ResponseEntity<TaskDTO>(taskManagerService.updateTask(taskDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/task/{id}")
	public ResponseEntity<String> deleteTask(@PathVariable("id") long id) throws TaskNotFoundException {
		return new ResponseEntity<String>(taskManagerService.deleteTask(id), HttpStatus.OK);
	}
}
