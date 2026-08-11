package com.ravi.reportingwebsiteenfos.repository;

import com.ravi.reportingwebsiteenfos.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    Optional<Project> findTopByOrderByUpdatedAtDesc();
}
