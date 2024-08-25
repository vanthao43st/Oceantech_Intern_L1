package com.globits.da.service;

import com.globits.da.dto.EmployeeDto;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface EmployeeService {
    List<EmployeeDto> getAllEmployee();
}
