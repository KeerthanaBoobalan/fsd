package com.fsd.task.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TaskDTO {

	@JsonProperty("taskId")	
	private Long taskId;
	
	@JsonProperty("priority")	
	private Long priority;
	
	@JsonProperty("taskName")	
	private String taskName;
	
	@JsonProperty("startDate")	
	private Date startDate;
	
	@JsonProperty("endDate")	
	private Date endDate;
	
	@JsonProperty("parentTask")	
	private String parentTask;

	public Long getTaskId() {
		return taskId;
	}
	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	public Long getPriority() {
		return priority;
	}
	public void setPriority(Long priority) {
		this.priority = priority;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getParentTask() {
		return parentTask;
	}
	public void setParentTask(String parentTask) {
		this.parentTask = parentTask;
	}	
}
