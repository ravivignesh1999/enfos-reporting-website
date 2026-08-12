package com.ravi.reportingwebsiteenfos.repository;

import com.ravi.reportingwebsiteenfos.model.Project;
import com.ravi.reportingwebsiteenfos.model.ProjectStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Seeds the Projects table at startup. */
@Component
@RequiredArgsConstructor
public class ProjectMockDataSeeder implements CommandLineRunner {

    private final ProjectRepository projectRepository;

    @Override
    public void run(String... args) {
        projectRepository.saveAll(buildProjects());
    }

    private static List<Project> buildProjects() {
        List<Project> rows = new ArrayList<>();
        long id = 1;

        // ~70% good rows (28).
        String[] projectNames = {
                "Customer Portal Revamp", "Billing Migration", "Mobile App Launch", "Data Warehouse Upgrade",
                "Vendor Onboarding Automation", "Internal Analytics Dashboard", "API Gateway Rollout",
                "Security Audit Remediation", "Search Relevance Overhaul", "Payments Reconciliation Tool",
                "Employee Self-Service Portal", "Cloud Cost Optimization", "Localization Expansion",
                "Accessibility Compliance Sprint", "Sales Forecasting Model", "Support Ticket Triage Bot",
                "Warehouse Inventory Sync", "Marketing Attribution Pipeline", "Legacy System Decommission",
                "Partner API Integration", "Contract Lifecycle Tool", "Real-Time Alerting Platform",
                "Customer Health Scoring", "Onboarding Flow Redesign", "Data Retention Cleanup",
                "Single Sign-On Rollout", "Performance Testing Suite", "Field Sales Enablement App"
        };
        String[] departments = {
                "Engineering", "Sales", "Marketing", "Product", "Finance",
                "IT", "Support", "Operations", "Data & Analytics", "Design"
        };
        Random random = new Random(99);
        for (int i = 0; i < 28; i++) {
            String owner = MockPersonNames.FIRST_NAMES[(i * 3) % MockPersonNames.FIRST_NAMES.length]
                    + " " + MockPersonNames.LAST_NAMES[(i * 11) % MockPersonNames.LAST_NAMES.length];
            ProjectStatus status = ProjectStatus.values()[i % ProjectStatus.values().length];
            LocalDate start = LocalDate.of(2021, 1, 1).plusDays(random.nextInt(1600));
            LocalDate end = start.plusDays(30 + random.nextInt(400));
            rows.add(Project.builder()
                    .id(id++)
                    .name(projectNames[i % projectNames.length])
                    .department(departments[i % departments.length])
                    .owner(owner)
                    .status(status.name())
                    .startDate(start)
                    .endDate(end)
                    .build());
        }

        // ~15% missing/null optional-looking fields (6): mostly "still active, no end date yet".
        rows.add(Project.builder().id(id++).name("Checkout Redesign").department("Engineering").owner("Nadia Ibrahim").status(ProjectStatus.ACTIVE.name()).startDate(LocalDate.of(2026, 2, 3)).endDate(null).build());
        rows.add(Project.builder().id(id++).name("Fraud Detection Model").department("Data & Analytics").owner("Felix Grant").status(ProjectStatus.ACTIVE.name()).startDate(LocalDate.of(2026, 5, 12)).endDate(null).build());
        rows.add(Project.builder().id(id++).name("Support Macro Library").department("Support").owner("Hana Kim").status(ProjectStatus.ACTIVE.name()).startDate(LocalDate.of(2026, 6, 20)).endDate(null).build());
        rows.add(Project.builder().id(id++).name("Vendor Risk Review").department("Operations").owner(null).status(ProjectStatus.ON_HOLD.name()).startDate(LocalDate.of(2025, 9, 1)).endDate(null).build());
        rows.add(Project.builder().id(id++).name("Design System v2").department("Design").owner(null).status(ProjectStatus.ACTIVE.name()).startDate(LocalDate.of(2026, 1, 15)).endDate(LocalDate.of(2026, 10, 1)).build());
        rows.add(Project.builder().id(id++).name("Regional Tax Compliance").department(null).owner("Chloe O'Connor").status(ProjectStatus.COMPLETED.name()).startDate(LocalDate.of(2024, 3, 1)).endDate(LocalDate.of(2024, 11, 30)).build());

        // ~10% edge-case/malformed (4).
        rows.add(Project.builder().id(id++).name("Q3 Cross-Functional Multi-Region Initiative for Long-Term Strategic Platform Consolidation and Beyond").department("Engineering").owner("Diego Martinez").status(ProjectStatus.ACTIVE.name()).startDate(LocalDate.of(2025, 4, 1)).endDate(LocalDate.of(2025, 12, 1)).build());
        rows.add(Project.builder().id(id++).name("Piñata 🎉 Launch Campaign").department("Marketing").owner("Zoe Yilmaz").status(ProjectStatus.ON_HOLD.name()).startDate(LocalDate.of(2025, 6, 1)).endDate(LocalDate.of(2025, 8, 1)).build());
        rows.add(Project.builder().id(id++).name("Archived System Migration").department("IT").owner("Ravi Patel").status(ProjectStatus.COMPLETED.name()).startDate(LocalDate.of(2099, 1, 1)).endDate(LocalDate.of(2010, 1, 1)).build());
        rows.add(Project.builder().id(id++).name("<b>Bold</b> Rebrand Project").department("Sales").owner("Leo Bishop").status(ProjectStatus.CANCELLED.name()).startDate(LocalDate.of(1899, 1, 1)).endDate(LocalDate.of(1899, 6, 1)).build());

        // ~5% empty-ish (2): only the id is reliable.
        rows.add(Project.builder().id(id++).build());
        rows.add(Project.builder().id(id++).name("").department(null).owner(null).status(null).startDate(null).endDate(null).build());

        return rows;
    }
}
