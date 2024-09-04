package com.globits.da.service;

import com.globits.core.service.GenericService;
import com.globits.da.domain.Employee;
import com.globits.da.dto.EmployeeDto;
import com.globits.da.dto.search.EmployeeSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EmployeeService extends GenericService<Employee, Integer> {
    List<EmployeeDto> getAllEmployee();
    Page<EmployeeDto> searchByPage(EmployeeSearchDto dto);
}