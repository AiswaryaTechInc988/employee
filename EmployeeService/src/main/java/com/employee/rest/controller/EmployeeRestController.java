package com.employee.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.employee.rest.domain.EmployeeDTO;
import com.employee.rest.domain.EmployeeVO;
import com.employee.services.EmployeeService;

@RestController
@RequestMapping("api/employee")
public class EmployeeRestController {

	@Autowired
	private EmployeeService service;

	@GetMapping("{id}")
	public ResponseEntity<EmployeeVO> getById(@PathVariable("id") Integer id) {
		return new ResponseEntity<>(service.findEmployeeByID(id), HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<EmployeeVO>> getAllEmployees() {
		return new ResponseEntity<>(service.getEmployees(), HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<EmployeeVO> addEmployee(@RequestBody EmployeeDTO employeeVo) {
		return new ResponseEntity<>(service.createEmployee(employeeVo), HttpStatus.CREATED);
	}

	@PutMapping
	public ResponseEntity<EmployeeVO> updateEmployee(@RequestBody EmployeeDTO employee) {
		return new ResponseEntity<>(service.updateEmployee(employee), HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<Void> deleteEmployee(@PathVariable("id") Integer id) {
		service.deleteEmployee(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

}
