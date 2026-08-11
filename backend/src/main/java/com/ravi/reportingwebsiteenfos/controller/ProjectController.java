package com.ravi.reportingwebsiteenfos.controller;

import com.ravi.reportingwebsiteenfos.dto.PagedResponse;
import com.ravi.reportingwebsiteenfos.dto.ProjectResponse;
import com.ravi.reportingwebsiteenfos.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/reports/projects")
@RequiredArgsConstructor
public class ProjectController {

    private static final Set<String> SORTABLE_FIELDS = Set.of("id", "name", "status", "startDate", "endDate");

    private final ProjectService projectService;

    @GetMapping
    public PagedResponse<ProjectResponse> getProjects(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        SortValidator.validate(pageable.getSort(), SORTABLE_FIELDS);
        return PagedResponse.of(projectService.getProjects(pageable), ProjectResponse::from);
    }
}
