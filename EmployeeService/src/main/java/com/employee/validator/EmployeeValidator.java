package com.employee.validator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.employee.constants.ApplicationConstants;
import com.employee.exception.BaseException;
import com.employee.exception.EmployeeException;
import com.employee.rest.domain.EmployeeDTO;

import io.micrometer.core.instrument.util.StringUtils;

@Component
public class EmployeeValidator {
	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConstants.LOGGER_NAME);

	public void createValidator(EmployeeDTO dto) {
		LOGGER.debug("Validating employee fiedls");
		
		List<BaseException> exceptions = new ArrayList<>();
		
		if (dto != null) {
			if (StringUtils.isBlank(dto.getEmployeeName())) {
				BaseException exception = new BaseException();
				exception.setField("employeeName");
				exception.setMessage("Employee Name is mandatory");
				exceptions.add(exception);
				LOGGER.debug("employee name is missing");
			}
			
			if (StringUtils.isBlank(dto.getDepartment())) {
				BaseException exception = new BaseException();
				exception.setField("department");
				exception.setMessage("Employee Department is mandatory");
				exceptions.add(exception);
				LOGGER.debug("employee department is missing");

			}
			
			if (dto.getSalary() == null || dto.getSalary().compareTo(BigDecimal.ZERO) <= 0) {
				BaseException exception = new BaseException();
				exception.setField("salary");
				exception.setMessage("Employee Salary is mandatory");
				exceptions.add(exception);
				LOGGER.debug("employee salary is missing");

			}
		}
		if (!exceptions.isEmpty()) {
			throw new EmployeeException(exceptions);
		}
	}

}
