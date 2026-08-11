package com.ravi.reportingwebsiteenfos.repository;

import com.ravi.reportingwebsiteenfos.model.Department;
import com.ravi.reportingwebsiteenfos.model.Project;
import com.ravi.reportingwebsiteenfos.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MockDataSeedingTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void seedsFortyUsersWithUniqueIds() {
        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(40);
        assertThat(users.stream().map(User::getId).distinct().count()).isEqualTo(40);
    }

    @Test
    void seedsFortyDepartmentsWithUniqueIds() {
        List<Department> departments = departmentRepository.findAll();
        assertThat(departments).hasSize(40);
        assertThat(departments.stream().map(Department::getId).distinct().count()).isEqualTo(40);
    }

    @Test
    void seedsFortyProjectsWithUniqueIds() {
        List<Project> projects = projectRepository.findAll();
        assertThat(projects).hasSize(40);
        assertThat(projects.stream().map(Project::getId).distinct().count()).isEqualTo(40);
    }
}
