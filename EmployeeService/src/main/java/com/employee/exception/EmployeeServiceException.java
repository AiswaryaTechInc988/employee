package com.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EmployeeServiceException extends RuntimeException {

	private static final long serialVersionUID = 3995261841897484708L;
	public String message;
}
