package com.sample.simplemicroservice.service;

import com.sample.simplemicroservice.entity.Department;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IDepartmentService {

    List<Department> getAllDepartments();
    Optional<Department> getDepartmentById(UUID id);
    Department createDepartment(Department department);
    Optional<Department> updateDepartment(UUID id, Department departmentDetails);
    boolean deleteDepartment(UUID id);
}
