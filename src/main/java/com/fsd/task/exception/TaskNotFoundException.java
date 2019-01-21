package com.fsd.task.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TaskNotFoundException extends Exception {

public static final long serialVersionUID = 1L;
	
	public TaskNotFoundException(String message) {
		super(message);
	}
}
