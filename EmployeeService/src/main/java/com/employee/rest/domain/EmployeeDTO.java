package com.employee.rest.domain;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeDTO {

	private Integer employeeID;
	private String employeeName;
	private BigDecimal salary;
	private String department;

}