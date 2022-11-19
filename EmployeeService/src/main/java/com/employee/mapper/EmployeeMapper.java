package com.employee.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import com.employee.domain.Employee;
import com.employee.rest.domain.EmployeeDTO;
import com.employee.rest.domain.EmployeeVO;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {

	EmployeeMapper INSTANCE = Mappers.getMapper(EmployeeMapper.class);

	Employee getEmployee(EmployeeDTO companyDTO);

	EmployeeVO getEmployeeVo(Employee company);

	List<EmployeeVO> getEmployeeVOs(List<Employee> companies);
}
