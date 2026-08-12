package com.ravi.reportingwebsiteenfos.repository;

import com.ravi.reportingwebsiteenfos.model.User;
import com.ravi.reportingwebsiteenfos.model.UserStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** Seeds the Users table at startup. */
@Component
@RequiredArgsConstructor
public class UserMockDataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        userRepository.saveAll(buildUsers());
    }

    private static List<User> buildUsers() {
        List<User> rows = new ArrayList<>();
        long id = 1;

        // ~70% good rows (28): complete, realistic, well-formed.
        String[] roles = {
                "Admin", "Manager", "Engineer", "Analyst", "Support", "Sales",
                "HR Specialist", "Recruiter", "Finance Analyst", "Product Manager"
        };
        Random random = new Random(42);
        for (int i = 0; i < 28; i++) {
            String first = MockPersonNames.FIRST_NAMES[i % MockPersonNames.FIRST_NAMES.length];
            String last = MockPersonNames.LAST_NAMES[(i * 7) % MockPersonNames.LAST_NAMES.length];
            String email = (first + "." + last + "@enfos.com").toLowerCase();
            UserStatus status = UserStatus.values()[i % UserStatus.values().length];
            LocalDate created = LocalDate.of(2019, 1, 1).plusDays(random.nextInt(2600));
            rows.add(User.builder()
                    .id(id++)
                    .name(first + " " + last)
                    .email(email)
                    .role(roles[i % roles.length])
                    .status(status.name())
                    .createdDate(created)
                    .build());
        }

        // ~15% missing/null optional-looking fields (6).
        rows.add(User.builder().id(id++).name("Priya Natarajan").email("priya.natarajan@enfos.com").role(null).status(UserStatus.ACTIVE.name()).createdDate(LocalDate.of(2022, 3, 14)).build());
        rows.add(User.builder().id(id++).name("Marcus Webb").email("marcus.webb@enfos.com").role(null).status(UserStatus.PENDING.name()).createdDate(LocalDate.of(2023, 7, 2)).build());
        rows.add(User.builder().id(id++).name("Elena Cho").email("elena.cho@enfos.com").role("Engineer").status(null).createdDate(LocalDate.of(2021, 11, 30)).build());
        rows.add(User.builder().id(id++).name("Tomas Reyes").email("tomas.reyes@enfos.com").role("Support").status(null).createdDate(LocalDate.of(2020, 5, 19)).build());
        rows.add(User.builder().id(id++).name("Aisha Bello").email(null).role("Analyst").status(UserStatus.ACTIVE.name()).createdDate(LocalDate.of(2024, 1, 9)).build());
        rows.add(User.builder().id(id++).name("Grace Lindqvist").email("grace.lindqvist@enfos.com").role("HR Specialist").status(UserStatus.ACTIVE.name()).createdDate(null).build());

        // ~10% edge-case/malformed (4).
        rows.add(User.builder().id(id++).name("Bartholomew Alexander Fitzgerald-Worthington the Third of Nowhereville").email("not-an-email").role("Engineer").status(UserStatus.ACTIVE.name()).createdDate(LocalDate.of(2023, 6, 1)).build());
        rows.add(User.builder().id(id++).name("Zoë Ñuñez-O'Brien 😀").email("zoe.nunez@enfos.com").role("Manager").status(UserStatus.INACTIVE.name()).createdDate(LocalDate.of(2022, 2, 2)).build());
        rows.add(User.builder().id(id++).name("Kwame Asante").email("kwame.asante@@enfos..com").role("Sales").status(UserStatus.ACTIVE.name()).createdDate(LocalDate.of(2099, 12, 31)).build());
        rows.add(User.builder().id(id++).name("<script>alert(1)</script>").email("test@enfos.com").role("Support").status(UserStatus.PENDING.name()).createdDate(LocalDate.of(1899, 1, 1)).build());

        // ~5% empty-ish (2): only the id is reliable.
        rows.add(User.builder().id(id++).build());
        rows.add(User.builder().id(id++).name("").email(null).role(null).status(null).createdDate(null).build());

        return rows;
    }
}
