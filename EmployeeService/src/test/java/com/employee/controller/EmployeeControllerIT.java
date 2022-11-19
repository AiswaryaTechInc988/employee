package com.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.math.BigDecimal;
import java.util.List;

import org.json.JSONException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.employee.EmployeeServiceApplication;
import com.employee.exception.BaseException;
import com.employee.exception.EmployeeNotFoundException;
import com.employee.rest.domain.EmployeeDTO;
import com.employee.rest.domain.EmployeeVO;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = EmployeeServiceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(OrderAnnotation.class)
class EmployeeControllerIT {

	@LocalServerPort
	private int port;

	TestRestTemplate restTemplate = new TestRestTemplate();

	HttpHeaders headers = new HttpHeaders();

	private String createURLWithPort(String uri) {
		return "http://localhost:" + port + uri;
	}

	@Test
	@DisplayName("200 - Create success")
	@Order(1)
	void testRetrieveStudentCourse() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);

		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(dto, headers);

		ResponseEntity<EmployeeVO> response = restTemplate.exchange(createURLWithPort("/api/employee"), HttpMethod.POST,
				entity, EmployeeVO.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeVO vo = response.getBody();
		assertEquals(1, vo.getEmployeeID());
		assertEquals("Chandra", vo.getEmployeeName());
		assertEquals("IT", vo.getDepartment());
		assertEquals(BigDecimal.ONE, vo.getSalary());

	}
	
	@Test
	@DisplayName("417 - null employee data")
	@Order(2)
	void create417InvalidName() {

		EmployeeDTO dto = new EmployeeDTO();

		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(dto, headers);

		ResponseEntity<List<BaseException>> response = restTemplate.exchange(createURLWithPort("/api/employee"), HttpMethod.POST,
				entity, new ParameterizedTypeReference<List<BaseException>>(){});

		assertNotNull(response);
		assertNotNull(response.getBody());
		List<BaseException> exception = response.getBody();
		assertEquals(exception.size(), 3);
		assertEquals("employeeName", exception.get(0).getField());
		assertEquals("Employee Name is mandatory", exception.get(0).getMessage());
		assertEquals("department", exception.get(1).getField());
		assertEquals("Employee Department is mandatory", exception.get(1).getMessage());
		assertEquals("salary", exception.get(2).getField());
		assertEquals("Employee Salary is mandatory", exception.get(2).getMessage());

	}
	
	
	@Test
	@DisplayName("200 - Get All")
	@Order(3)
	void getAllEmployees() throws JSONException {
		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(null, headers);

		ResponseEntity<List<EmployeeVO>> response = restTemplate.exchange(createURLWithPort("/api/employee"), HttpMethod.GET,
				entity, new ParameterizedTypeReference<List<EmployeeVO>>(){});

		assertNotNull(response);
		assertNotNull(response.getBody());
		List<EmployeeVO> vo = response.getBody();
		assertEquals(1, vo.size());

	}
	
	@Test
	@DisplayName("200 - Get by ID")
	@Order(4)
	void getEmployeeByID() throws JSONException {
		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(null, headers);

		ResponseEntity<EmployeeVO> response = restTemplate.exchange(createURLWithPort("/api/employee/1"), HttpMethod.GET,
				entity, EmployeeVO.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeVO vo = response.getBody();
		assertEquals(1, vo.getEmployeeID());
		assertEquals("Chandra", vo.getEmployeeName());
		assertEquals("IT", vo.getDepartment());
		assertEquals(0, vo.getSalary().compareTo(BigDecimal.ONE));
	}
	
	@Test
	@DisplayName("417 - Get by ID")
	@Order(5)
	void getEmployeeByID_notFOund() throws JSONException {
		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(null, headers);

		ResponseEntity<EmployeeNotFoundException> response = restTemplate.exchange(createURLWithPort("/api/employee/2"), HttpMethod.GET,
				entity, EmployeeNotFoundException.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeNotFoundException vo = response.getBody();
		assertEquals("No records avaialble with ID 2", vo.getMessage());
	}
	
	@Test
	@DisplayName("200 - update Employee")
	@Order(6)
	void updateEmployee() throws JSONException {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(dto, headers);

		ResponseEntity<EmployeeVO> response = restTemplate.exchange(createURLWithPort("/api/employee"), HttpMethod.PUT,
				entity, EmployeeVO.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeVO vo = response.getBody();
		assertEquals(1, vo.getEmployeeID());
		assertEquals("Chandra", vo.getEmployeeName());
		assertEquals("IT", vo.getDepartment());
		assertEquals(BigDecimal.ONE, vo.getSalary());
	}
	
	@Test
	@DisplayName("417 - not found")
	@Order(7)
	void updateEmployee_notFOund() throws JSONException {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(2);
		
		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(dto, headers);
		
		ResponseEntity<EmployeeNotFoundException> response = restTemplate.exchange(createURLWithPort("/api/employee"), HttpMethod.PUT,
				entity, EmployeeNotFoundException.class);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeNotFoundException vo = response.getBody();
		assertEquals("No records avaialble with ID 2", vo.getMessage());
	}
	
	@Test
	@DisplayName("200 - delete Employee")
	@Order(8)
	void deleteEmployee() throws JSONException {
		HttpEntity<EmployeeDTO> entity = new HttpEntity<>(null, headers);

		ResponseEntity<EmployeeVO> response = restTemplate.exchange(createURLWithPort("/api/employee/1"), HttpMethod.DELETE,
				entity, EmployeeVO.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

}
