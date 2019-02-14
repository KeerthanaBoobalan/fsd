package com.fsd.task.service;

import java.util.List;

import com.fsd.task.dto.ProjectDTO;
import com.fsd.task.exception.ProjectNotFoundException;

public interface ProjectService {

	List<ProjectDTO> getAllProjects(String sort,String sortDirection);
	ProjectDTO addProject(ProjectDTO projectDTO);
	ProjectDTO updateProject(ProjectDTO projectDTO) throws ProjectNotFoundException;
	String deleteProject(Long projectId) throws ProjectNotFoundException;
	
}
