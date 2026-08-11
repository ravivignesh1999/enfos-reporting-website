package com.ravi.reportingwebsiteenfos.controller;

import com.ravi.reportingwebsiteenfos.dto.DepartmentResponse;
import com.ravi.reportingwebsiteenfos.dto.PagedResponse;
import com.ravi.reportingwebsiteenfos.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/reports/departments")
@RequiredArgsConstructor
public class DepartmentController {

    private static final Set<String> SORTABLE_FIELDS = Set.of("id", "name", "employeeCount", "location");

    private final DepartmentService departmentService;

    @GetMapping
    public PagedResponse<DepartmentResponse> getDepartments(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        SortValidator.validate(pageable.getSort(), SORTABLE_FIELDS);
        return PagedResponse.of(departmentService.getDepartments(pageable), DepartmentResponse::from);
    }
}
