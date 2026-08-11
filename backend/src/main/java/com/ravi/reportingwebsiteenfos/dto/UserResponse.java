package com.ravi.reportingwebsiteenfos.dto;

import com.ravi.reportingwebsiteenfos.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String role;
    private String status;
    private LocalDate createdDate;
    private Instant createdAt;
    private Instant updatedAt;

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getRole(),
                user.getStatus(),
                user.getCreatedDate(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}
