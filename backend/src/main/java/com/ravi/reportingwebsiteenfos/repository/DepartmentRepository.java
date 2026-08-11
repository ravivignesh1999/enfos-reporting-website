package com.ravi.reportingwebsiteenfos.repository;

import com.ravi.reportingwebsiteenfos.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findTopByOrderByUpdatedAtDesc();
}
