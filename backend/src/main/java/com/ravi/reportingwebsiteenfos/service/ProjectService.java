package com.ravi.reportingwebsiteenfos.service;

import com.ravi.reportingwebsiteenfos.model.Project;
import com.ravi.reportingwebsiteenfos.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public Page<Project> getProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
    }
}
