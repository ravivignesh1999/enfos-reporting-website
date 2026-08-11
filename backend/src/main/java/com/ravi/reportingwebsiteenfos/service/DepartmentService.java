package com.ravi.reportingwebsiteenfos.service;

import com.ravi.reportingwebsiteenfos.model.Department;
import com.ravi.reportingwebsiteenfos.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
}
