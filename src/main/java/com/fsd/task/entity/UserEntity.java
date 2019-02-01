package com.fsd.task.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "User", schema = "fsd")
public class UserEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "user_id")
	private Long userId;
	
	@Column(name = "first_name")
	private String firstName;

	/*
	 * @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	 * 
	 * @JoinColumn(name = "parent_id") private ParentTask parentTask;
	 */
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "emp_id")
	private String empId;
	
	@Column(name = "proj_id")
	private Long projId;
	
	@Column(name = "task_id")
	private Long taskId;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public Long getProjId() {
		return projId;
	}

	public void setProjId(Long projId) {
		this.projId = projId;
	}

	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}
	
	
}