package com.employee.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.employee.domain.Employee;
import com.employee.exception.EmployeeException;
import com.employee.exception.EmployeeServiceException;
import com.employee.mapper.EmployeeMapper;
import com.employee.repository.EmployeeRepository;
import com.employee.rest.controller.EmployeeRestController;
import com.employee.rest.domain.EmployeeDTO;
import com.employee.rest.domain.EmployeeVO;
import com.employee.services.EmployeeService;
import com.employee.validator.EmployeeValidator;

@ExtendWith(SpringExtension.class)
@TestMethodOrder(OrderAnnotation.class)
class EmployeeControllerTest {

	@Mock
	private EmployeeRepository repository;

	private EmployeeRestController controller;
	
	@BeforeEach
    public void setUp() {
		mock(EmployeeRepository.class);
		EmployeeValidator validator = new EmployeeValidator();
		EmployeeService service = new EmployeeService(repository, validator);
		controller = new EmployeeRestController(service);
		
    }

	@Test
	@DisplayName("Create - success")
	@Order(1)
	void testRetrieveEmployee() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.save(any(Employee.class))).thenReturn(EmployeeMapper.INSTANCE.getEmployee(dto));
		
		ResponseEntity<EmployeeVO> response = controller.addEmployee(dto);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeVO vo = response.getBody();
		assertEquals(1, vo.getEmployeeID());
		assertEquals("Chandra", vo.getEmployeeName());
		assertEquals("IT", vo.getDepartment());
		assertEquals(BigDecimal.ONE, vo.getSalary());
	}
	
	@Test
	@DisplayName("create - exception")
	@Order(2)
	void testCreateServiceError() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.save(any(Employee.class))).thenThrow(new EmployeeServiceException("service error"));
		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(EmployeeServiceException.class, () -> controller.addEmployee(dto));
		assertEquals("Unexpected error occured while creating record service error", exception.getMessage());
	}
	
	@Test
	@DisplayName("create - validation Fail")
	@Order(3)
	void testEmployeeCreateFail() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		
		when(repository.save(any(Employee.class))).thenReturn(EmployeeMapper.INSTANCE.getEmployee(dto));
		
		EmployeeException exception = org.junit.jupiter.api.Assertions.assertThrows(EmployeeException.class, () -> controller.addEmployee(dto));
		assertEquals(3, exception.exceptions.size());
	}
	
	@Test
	@DisplayName("get All - exception")
	@Order(4)
	void testGetAllServiceError() throws JSONException {
		when(repository.findAll()).thenThrow(new EmployeeServiceException("service error"));
		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(EmployeeServiceException.class, () -> controller.getAllEmployees());
		assertEquals("Unexpected error occured while fetching employees service error", exception.getMessage());
	}
	
	@Test
	@DisplayName("get by ID - exception")
	@Order(5)
	void testGetByIDServiceError() throws JSONException {
		when(repository.findById(any(Integer.class))).thenThrow(new EmployeeServiceException("service error"));
		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(EmployeeServiceException.class, () -> controller.getById(1));
		assertEquals("Unexpected error occured while fetching employee service error", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update - exception")
	@Order(6)
	void testUpdateServiceError() throws JSONException {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(EmployeeMapper.INSTANCE.getEmployee(dto)));
		when(repository.save(any(Employee.class))).thenThrow(new EmployeeServiceException("service error"));
		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(EmployeeServiceException.class, () -> controller.updateEmployee(dto));
		assertEquals("Unexpected error occured while updating record service error", exception.getMessage());
	}

	@Test
	@DisplayName("Delete - exception")
	@Order(7)
	void testDeleteServiceError() throws JSONException {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(EmployeeMapper.INSTANCE.getEmployee(dto)));
		doThrow(new EmployeeServiceException("service error")).when(repository).deleteById(any(Integer.class));
		Exception exception = org.junit.jupiter.api.Assertions.assertThrows(EmployeeServiceException.class, () -> controller.deleteEmployee(1));
		assertEquals("Unexpected error occured while deeleting record service error", exception.getMessage());
	}
	
	@Test
	@DisplayName("Update - success")
	@Order(8)
	void testUpdate() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(EmployeeMapper.INSTANCE.getEmployee(dto)));
		when(repository.save(any(Employee.class))).thenReturn(EmployeeMapper.INSTANCE.getEmployee(dto));
		
		ResponseEntity<EmployeeVO> response = controller.updateEmployee(dto);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeVO vo = response.getBody();
		assertEquals(1, vo.getEmployeeID());
		assertEquals("Chandra", vo.getEmployeeName());
		assertEquals("IT", vo.getDepartment());
		assertEquals(BigDecimal.ONE, vo.getSalary());
	}
	
	@Test
	@DisplayName("find all - success")
	@Order(8)
	void testfindAll() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.findAll()).thenReturn(List.of(EmployeeMapper.INSTANCE.getEmployee(dto)));
		
		ResponseEntity<List<EmployeeVO>> response = controller.getAllEmployees();

		assertNotNull(response);
		assertNotNull(response.getBody());
		List<EmployeeVO> vo = response.getBody();
		assertNotNull(vo);
		assertEquals(1, vo.size());
		assertEquals(1, vo.get(0).getEmployeeID());
		assertEquals("Chandra", vo.get(0).getEmployeeName());
		assertEquals("IT", vo.get(0).getDepartment());
		assertEquals(BigDecimal.ONE, vo.get(0).getSalary());
	}
	
	@Test
	@DisplayName("find by id - success")
	@Order(8)
	void testfindById() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(EmployeeMapper.INSTANCE.getEmployee(dto)));
		
		ResponseEntity<EmployeeVO> response = controller.getById(1);

		assertNotNull(response);
		assertNotNull(response.getBody());
		EmployeeVO vo = response.getBody();
		assertEquals(1, vo.getEmployeeID());
		assertEquals("Chandra", vo.getEmployeeName());
		assertEquals("IT", vo.getDepartment());
		assertEquals(BigDecimal.ONE, vo.getSalary());
	}
	
	@Test
	@DisplayName("delete - success")
	@Order(8)
	void testDeleteById() throws JSONException {

		EmployeeDTO dto = new EmployeeDTO();
		dto.setEmployeeName("Chandra");
		dto.setDepartment("IT");
		dto.setSalary(BigDecimal.ONE);
		dto.setEmployeeID(1);
		
		when(repository.findById(any(Integer.class))).thenReturn(Optional.of(EmployeeMapper.INSTANCE.getEmployee(dto)));
		
		ResponseEntity<Void> response = controller.deleteEmployee(1);

		assertNotNull(response);
		assertNotNull(response.getStatusCode());
	}
}
