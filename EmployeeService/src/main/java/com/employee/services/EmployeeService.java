package com.employee.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.employee.constants.ApplicationConstants;
import com.employee.domain.Employee;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.exception.EmployeeServiceException;
import com.employee.mapper.EmployeeMapper;
import com.employee.repository.EmployeeRepository;
import com.employee.rest.domain.EmployeeDTO;
import com.employee.rest.domain.EmployeeVO;
import com.employee.validator.EmployeeValidator;

@Service
public class EmployeeService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationConstants.LOGGER_NAME);

	@Autowired
	public EmployeeRepository employeeRepository;

	@Autowired
	public EmployeeValidator employeeValidator;

	public EmployeeVO createEmployee(EmployeeDTO dto) {
		LOGGER.debug("Employee record creation is started");

		EmployeeVO vo = null;

		employeeValidator.createValidator(dto);

		Employee employee = EmployeeMapper.INSTANCE.getEmployee(dto);
		try {
			employee = employeeRepository.save(employee);
			LOGGER.info("Employee Record created");
			vo = EmployeeMapper.INSTANCE.getEmployeeVo(employee);
		} catch (Exception e) {
			throw new EmployeeServiceException("Unexpected error occured while creating record " + e.getMessage());
		}

		return vo;
	}

	public List<EmployeeVO> getEmployees() {
		LOGGER.debug("Fetching employee records is started");

		List<EmployeeVO> vo = null;

		try {
			// Create company data
			List<Employee> employees = employeeRepository.findAll();
			LOGGER.info("employees records count" + employees.size());
			vo = EmployeeMapper.INSTANCE.getEmployeeVOs(employees);
		} catch (Exception e) {
			throw new EmployeeServiceException("Unexpected error occured while fetching employees " + e.getMessage());
		}

		return vo;
	}

	public EmployeeVO findEmployeeByID(Integer id) {
		LOGGER.debug("Fetching employee records with ID " + id);

		EmployeeVO vo = null;
		Optional<Employee> employee;

		try {
			employee = employeeRepository.findById(id);
		} catch (Exception e) {
			throw new EmployeeServiceException("Unexpected error occured while fetching employees " + e.getMessage());
		}

		if (employee.isPresent()) {
			vo = EmployeeMapper.INSTANCE.getEmployeeVo(employee.get());
		} else {
			LOGGER.error("No records found with specified ID "+ id);
			throw new EmployeeNotFoundException("No records avaialble with ID " + id);
		}

		return vo;
	}

	public EmployeeVO updateEmployee(EmployeeDTO dto) {
		LOGGER.debug("Employee record updation is started");

		EmployeeVO vo = null;

		findEmployeeByID(dto.getEmployeeID());

		employeeValidator.createValidator(dto);

		try {
			Employee employee = EmployeeMapper.INSTANCE.getEmployee(dto);
			employee = employeeRepository.save(employee);

			LOGGER.info("Employee Record Updated");

			vo = EmployeeMapper.INSTANCE.getEmployeeVo(employee);
		} catch (Exception e) {
			throw new EmployeeServiceException("Unexpected error occured while updating record " + e.getMessage());
		}

		return vo;
	}

	public void deleteEmployee(Integer id) {
		LOGGER.debug("Employee record delete process is started for id " + id);
		findEmployeeByID(id);

		try {
			employeeRepository.deleteById(id);
			LOGGER.info("Employee Record deleted id :: " + id);
		} catch (Exception e) {
			throw new EmployeeServiceException("Unexpected error occured while deeleting record " + e.getMessage());
		}
	}

}
