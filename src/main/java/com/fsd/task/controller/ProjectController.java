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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fsd.task.dto.ProjectDTO;
import com.fsd.task.exception.ProjectNotFoundException;
import com.fsd.task.service.ProjectService;

@RestController
@RequestMapping("/")
public class ProjectController {

	@Autowired
	ProjectService projectService;
	
	
	@GetMapping(value = "/projects", produces = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<List<ProjectDTO>> getAllProjects(@RequestParam(value = "sort", required = false) String sort,
			@RequestParam(value = "sortDirection", required = false) String sortDirection) {
		return new ResponseEntity<List<ProjectDTO>>(projectService.getAllProjects(sort,sortDirection), HttpStatus.OK);
	}
	
	@PostMapping(value = "/project", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ProjectDTO> addProject(@RequestBody ProjectDTO projectDTO) {
		return new ResponseEntity<ProjectDTO>(projectService.addProject(projectDTO), HttpStatus.OK);
	}
	
	@PutMapping(value = "/project/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	@CrossOrigin
	public ResponseEntity<ProjectDTO> updateProject(@RequestBody ProjectDTO projectDTO) throws ProjectNotFoundException {
		return new ResponseEntity<ProjectDTO>(projectService.updateProject(projectDTO), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/project/{id}")
	@CrossOrigin
	public ResponseEntity<String> deleteProject(@PathVariable("id") long id) throws ProjectNotFoundException {
		return new ResponseEntity<String>(projectService.deleteProject(id), HttpStatus.OK);
	}
}
