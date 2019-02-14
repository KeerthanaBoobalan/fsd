package com.fsd.task.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParentTaskDTO {

	@JsonProperty("parentId")	
	private Long parentId;
	
	@JsonProperty("parentTaskName")	
	private String parentTaskName;

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getParentTaskName() {
		return parentTaskName;
	}

	public void setParentTaskName(String parentTaskName) {
		this.parentTaskName = parentTaskName;
	}
	
}
