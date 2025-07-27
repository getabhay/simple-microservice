package com.sample.simplemicroservice.dto;

import com.sample.simplemicroservice.entity.enums.EmployeeType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {

    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private EmployeeType employeeType;
    private String departmentName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
