package com.ravi.reportingwebsiteenfos.service;

import com.ravi.reportingwebsiteenfos.dto.ReportMetadata;
import com.ravi.reportingwebsiteenfos.model.Department;
import com.ravi.reportingwebsiteenfos.model.Project;
import com.ravi.reportingwebsiteenfos.model.User;
import com.ravi.reportingwebsiteenfos.repository.DepartmentRepository;
import com.ravi.reportingwebsiteenfos.repository.ProjectRepository;
import com.ravi.reportingwebsiteenfos.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/** Backs GET /api/reports. */
@Service
@RequiredArgsConstructor
public class ReportCatalogService {

    private final UserRepository userRepository;
    private final DepartmentRepository departmentRepository;
    private final ProjectRepository projectRepository;

    public List<ReportMetadata> getAvailableReports() {
        return List.of(
                ReportMetadata.builder()
                        .id("users")
                        .name("Users")
                        .description("People in the system, their roles, and account status.")
                        .rowCount(userRepository.count())
                        .lastUpdated(userRepository.findTopByOrderByUpdatedAtDesc()
                                .map(User::getUpdatedAt).orElse(null))
                        .build(),
                ReportMetadata.builder()
                        .id("departments")
                        .name("Departments")
                        .description("Org structure across teams, managers, and locations.")
                        .rowCount(departmentRepository.count())
                        .lastUpdated(departmentRepository.findTopByOrderByUpdatedAtDesc()
                                .map(Department::getUpdatedAt).orElse(null))
                        .build(),
                ReportMetadata.builder()
                        .id("projects")
                        .name("Projects")
                        .description("Active and past work across departments.")
                        .rowCount(projectRepository.count())
                        .lastUpdated(projectRepository.findTopByOrderByUpdatedAtDesc()
                                .map(Project::getUpdatedAt).orElse(null))
                        .build()
        );
    }
}
