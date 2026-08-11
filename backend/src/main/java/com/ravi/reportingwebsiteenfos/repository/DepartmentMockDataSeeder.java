package com.ravi.reportingwebsiteenfos.repository;

import com.ravi.reportingwebsiteenfos.model.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Seeds the Departments table at startup. */
@Component
@RequiredArgsConstructor
public class DepartmentMockDataSeeder implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {
        departmentRepository.saveAll(buildDepartments());
    }

    private static List<Department> buildDepartments() {
        List<Department> rows = new ArrayList<>();
        long id = 1;

        // ~70% good rows (28).
        String[] deptTypes = {
                "Engineering", "Sales", "Marketing", "Human Resources", "Finance", "Legal",
                "Customer Support", "Product", "Operations", "IT", "Design",
                "Quality Assurance", "Data & Analytics", "Security", "Procurement"
        };
        String[] regions = {"", " - West", " - East", " - APAC", " - EMEA"};
        String[] locations = {
                "New York, NY", "San Francisco, CA", "Austin, TX", "London, UK", "Berlin, DE",
                "Singapore", "Toronto, CA", "Remote", "Chicago, IL", "Seattle, WA"
        };
        Random random = new Random(7);
        for (int i = 0; i < 28; i++) {
            String type = deptTypes[i % deptTypes.length];
            String region = regions[(i / deptTypes.length) % regions.length];
            String manager = MockPersonNames.FIRST_NAMES[i % MockPersonNames.FIRST_NAMES.length]
                    + " " + MockPersonNames.LAST_NAMES[(i * 5) % MockPersonNames.LAST_NAMES.length];
            int employeeCount = 4 + random.nextInt(140);
            rows.add(Department.builder()
                    .id(id++)
                    .name(type + region)
                    .manager(manager)
                    .employeeCount(employeeCount)
                    .location(locations[i % locations.length])
                    .build());
        }

        // ~15% missing/null optional-looking fields (6).
        rows.add(Department.builder().id(id++).name("Engineering - South").manager(null).employeeCount(52).location("Denver, CO").build());
        rows.add(Department.builder().id(id++).name("Sales - LATAM").manager(null).employeeCount(19).location("Mexico City, MX").build());
        rows.add(Department.builder().id(id++).name("Marketing - Growth").manager("Nadia Ibrahim").employeeCount(14).location(null).build());
        rows.add(Department.builder().id(id++).name("Finance - Treasury").manager("Felix Grant").employeeCount(9).location(null).build());
        rows.add(Department.builder().id(id++).name("Product - Platform").manager("Hana Kim").employeeCount(null).location("Boston, MA").build());
        rows.add(Department.builder().id(id++).name("Support - Tier 2").manager("Omar Santos").employeeCount(null).location("Dublin, IE").build());

        // ~10% edge-case/malformed (4).
        rows.add(Department.builder().id(id++).name("Office of the Senior Vice President for Strategic Cross-Functional Initiatives and Special Projects").manager("Diego Martinez").employeeCount(6).location("Remote").build());
        rows.add(Department.builder().id(id++).name("R&D 🚀 (Ñew Ventures)").manager("Chloe O'Connor").employeeCount(11).location("Zürich, CH").build());
        rows.add(Department.builder().id(id++).name("Legal").manager("Leo Bishop").employeeCount(-3).location("Chicago, IL").build());
        rows.add(Department.builder().id(id++).name("IT Helpdesk").manager("Ravi Patel").employeeCount(999999).location("12345").build());

        // ~5% empty-ish (2): only the id is reliable.
        rows.add(Department.builder().id(id++).build());
        rows.add(Department.builder().id(id++).name("").manager(null).employeeCount(null).location(null).build());

        return rows;
    }
}
