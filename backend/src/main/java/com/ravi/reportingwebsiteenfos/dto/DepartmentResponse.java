package com.ravi.reportingwebsiteenfos.dto;

import com.ravi.reportingwebsiteenfos.model.Department;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentResponse {

    private Long id;
    private String name;
    private String manager;
    private Integer employeeCount;
    private String location;
    private Instant createdAt;
    private Instant updatedAt;

    public static DepartmentResponse from(Department department) {
        return new DepartmentResponse(
                department.getId(),
                department.getName(),
                department.getManager(),
                department.getEmployeeCount(),
                department.getLocation(),
                department.getCreatedAt(),
                department.getUpdatedAt()
        );
    }
}
