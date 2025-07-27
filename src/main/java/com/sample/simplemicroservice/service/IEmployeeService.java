package com.sample.simplemicroservice.service;

import com.sample.simplemicroservice.dto.EmployeeDTO;
import com.sample.simplemicroservice.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IEmployeeService {

    List<EmployeeDTO> getAllEmployees();
    Optional<EmployeeDTO> getEmployeeById(UUID id);
    EmployeeDTO createEmployee(Employee employee, String departmentName);
    Optional<EmployeeDTO> updateEmployee(UUID id, Employee employeeDetails, String departmentName);
    boolean deleteEmployee(UUID id);
}
