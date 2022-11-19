package com.employee.advice;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.employee.exception.BaseException;
import com.employee.exception.EmployeeException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.EmployeeServiceException;

@ControllerAdvice
public class BaseExceptionController {
	
	@ExceptionHandler(value = EmployeeException.class)
	public ResponseEntity<List<BaseException>> exception(EmployeeException exception) {
		return new ResponseEntity<>(exception.getExceptions(), HttpStatus.EXPECTATION_FAILED);
	}
	
	@ExceptionHandler(value = EmployeeServiceException.class)
	public ResponseEntity<EmployeeServiceException> exception(EmployeeServiceException exception) {
		return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = EmployeeNotFoundException.class)
	public ResponseEntity<EmployeeNotFoundException> exception(EmployeeNotFoundException exception) {
		return new ResponseEntity<>(exception, HttpStatus.EXPECTATION_FAILED);
	}
}