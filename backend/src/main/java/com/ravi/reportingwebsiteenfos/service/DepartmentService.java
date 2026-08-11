package com.ravi.reportingwebsiteenfos.service;

import com.ravi.reportingwebsiteenfos.model.Department;
import com.ravi.reportingwebsiteenfos.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Page<Department> getDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }
}
