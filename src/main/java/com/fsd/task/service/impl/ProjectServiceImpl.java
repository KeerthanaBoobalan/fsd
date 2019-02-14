package com.fsd.task.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fsd.task.dto.ProjectDTO;
import com.fsd.task.entity.ProjectEntity;
import com.fsd.task.entity.UserEntity;
import com.fsd.task.exception.ProjectNotFoundException;
import com.fsd.task.repository.ProjectRepository;
import com.fsd.task.repository.UserRespository;
import com.fsd.task.service.ProjectService;

@Service("projectService")
public class ProjectServiceImpl implements ProjectService {

	@Autowired
	UserRespository userRepository;
	
	@Autowired
	ProjectRepository projectRepository;

	@Override
	public List<ProjectDTO> getAllProjects(String sort,String sortDirection) {
		return projectRepository.findAll(getSort(sort,sortDirection)).stream().map(projectEntity -> convertToProjectDto(projectEntity)).collect(Collectors.toList());
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
		projectDTO.setNoOfTasks(null != addedProject.getTasks() ? addedProject.getTasks().size() : 0);
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

	@Override
	public String deleteProject(Long projectId) throws ProjectNotFoundException {
		ProjectEntity project = getProjectById(projectId);
		projectRepository.delete(project);
		return "Project deleted Successfully";
	}

	private ProjectEntity getProjectById(Long projectId) throws ProjectNotFoundException {
		ProjectEntity project = projectRepository.findByProjectId(projectId).orElseThrow(() -> new ProjectNotFoundException("Project Not Found for the particular Id"));
		return project;
	}

	@Override
	public ProjectDTO updateProject(ProjectDTO projectDTO) throws ProjectNotFoundException {
		ProjectEntity project = getProjectById(projectDTO.getProjectId());
		return populateProject(projectDTO,project);
	}
	
	private Sort getSort(String sort,String sortDirection) {
		if(!StringUtils.isEmpty(sort) && !StringUtils.isEmpty(sortDirection)) {
			if("DESC".equalsIgnoreCase(sortDirection))
				return new Sort(Sort.Direction.DESC,sort);
			else
				return new Sort(Sort.Direction.ASC,sort);
		}
		return new Sort(Sort.Direction.ASC,"projectName");
	}
}
