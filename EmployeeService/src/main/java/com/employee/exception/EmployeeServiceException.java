package com.employee.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class EmployeeServiceException extends RuntimeException {

	private static final long serialVersionUID = 3995261841897484708L;
	public String message;
}
