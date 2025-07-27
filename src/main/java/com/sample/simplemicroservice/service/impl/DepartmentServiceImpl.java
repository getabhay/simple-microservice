package com.sample.simplemicroservice.service.impl;

import com.sample.simplemicroservice.entity.Department;
import com.sample.simplemicroservice.repository.DepartmentRepository;
import com.sample.simplemicroservice.service.IDepartmentService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DepartmentServiceImpl implements IDepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentServiceImpl(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public Optional<Department> getDepartmentById(UUID id) {
        return departmentRepository.findById(id);
    }

    @Override
    @Transactional
    public Department createDepartment(Department department) {
        Optional<Department> existingDepartment = departmentRepository.findByDepartmentName(department.getDepartmentName());
        if(existingDepartment.isPresent()) {
            throw new RuntimeException(department.getDepartmentName() + " already exists");
        }
        return departmentRepository.save(department);
    }

    @Override
    @Transactional
    public Optional<Department> updateDepartment(UUID id, Department departmentDetails) {
        return departmentRepository.findById(id).map(department -> {
            department.setDepartmentName(departmentDetails.getDepartmentName());
            department.setEmployees(departmentDetails.getEmployees());
            return departmentRepository.save(department);
        });
    }

    @Override
    @Transactional
    public boolean deleteDepartment(UUID id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
