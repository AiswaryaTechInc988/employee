package com.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3995261841897484708L;
	public String message;;
}
