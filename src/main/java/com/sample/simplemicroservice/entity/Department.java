package com.sample.simplemicroservice.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "departments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class Department {

    @Id
    @Column(name = "department_id", columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID departmentId;

    @NotBlank(message = "Department name cannot be blank")
    @Column(nullable = false, unique = true)
    private String departmentName;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference // <-- ADD THIS ANNOTATION
    private List<Employee> employees = new ArrayList<>();

    @PrePersist
    public void generateId() {
        if (this.departmentId == null) {
            this.departmentId = UUID.randomUUID();
        }
    }
}