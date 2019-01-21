package com.fsd.task.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import com.fsd.task.dto.ErrorDetails;

@ControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ValidationException.class)
	public ResponseEntity<?> handleValidationException(ValidationException validationException, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), validationException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(TaskNotFoundException.class)
	public ResponseEntity<?> handleValidationException(TaskNotFoundException taskNotFoundException, WebRequest request){
        ErrorDetails errorDetails = new ErrorDetails(new Date(), taskNotFoundException.getMessage(), request.getDescription(false));
        return new ResponseEntity<>(errorDetails,HttpStatus.NOT_FOUND);
	}
}
