package com.employee.exception;

import java.util.List;

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
public class EmployeeException extends RuntimeException {

	private static final long serialVersionUID = 3995261841897484708L;
	public List<BaseException> exceptions;
}
