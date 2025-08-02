package com.sample.simplemicroservice.service.impl;

import com.sample.simplemicroservice.dto.EmployeeDTO;
import com.sample.simplemicroservice.entity.Department;
import com.sample.simplemicroservice.entity.Employee;
import com.sample.simplemicroservice.repository.DepartmentRepository;
import com.sample.simplemicroservice.repository.EmployeeRepository;
import com.sample.simplemicroservice.service.IEmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements IEmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream().map(this::createEmployeeDTO).toList();
    }

    @Override
    public Optional<EmployeeDTO> getEmployeeById(UUID id) {
        return employeeRepository.findById(id).map(this::createEmployeeDTO);
    }

    @Override
    @Transactional
    public EmployeeDTO createEmployee(Employee employee, String departmentName) {
        return departmentRepository.findByDepartmentName(departmentName).map(department -> {
            employee.setDepartment(department);
            Employee updateEmployee = employeeRepository.save(employee);
            department.getEmployees().add(updateEmployee);
            departmentRepository.save(department);
            return createEmployeeDTO(employee);
        }).orElseThrow(() -> new NoSuchElementException(departmentName + " does not exist"));
    }

    @Override
    public Optional<EmployeeDTO> updateEmployee(UUID id, Employee employeeDetails, String departmentName) {
        return employeeRepository.findById(id).map(employee -> {
            employee.setFirstName(employeeDetails.getFirstName());
            employee.setLastName(employeeDetails.getLastName());
            employee.setEmail(employeeDetails.getEmail());
            employee.setMobileNumber(employeeDetails.getMobileNumber());
            employee.setEmployeeType(employeeDetails.getEmployeeType());

            if (departmentName != null) {
                departmentRepository.findByDepartmentName(employee.getDepartment().getDepartmentName()).
                        map(department -> {
                            department.getEmployees().remove(employee);
                            return departmentRepository.save(department);
                        });
                Optional<Department> newDepartment = departmentRepository.findByDepartmentName(departmentName);
                newDepartment.ifPresent(employee::setDepartment);
            }
            return createEmployeeDTO(employeeRepository.save(employee));
        });
    }

    @Override
    public boolean deleteEmployee(UUID id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EmployeeDTO createEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setId(employee.getEmployeeId());
        if (employee.getDepartment() != null) {
            employeeDTO.setDepartmentName(employee.getDepartment().getDepartmentName());
        } else {
            employeeDTO.setDepartmentName(null);
        }        employeeDTO.setEmployeeType(employee.getEmployeeType());
        employeeDTO.setFirstName(employee.getFirstName());
        employeeDTO.setLastName(employee.getLastName());
        employeeDTO.setEmail(employee.getEmail());
        employeeDTO.setMobileNumber(employee.getMobileNumber());
        employeeDTO.setCreatedAt(employee.getCreatedAt());
        employeeDTO.setUpdatedAt(employee.getUpdatedAt());
        return employeeDTO;
    }
}
