package com.ravi.reportingwebsiteenfos.service;

import com.ravi.reportingwebsiteenfos.model.Project;
import com.ravi.reportingwebsiteenfos.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
