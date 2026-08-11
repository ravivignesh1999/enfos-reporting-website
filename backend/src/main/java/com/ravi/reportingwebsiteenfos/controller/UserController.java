package com.ravi.reportingwebsiteenfos.controller;

import com.ravi.reportingwebsiteenfos.dto.PagedResponse;
import com.ravi.reportingwebsiteenfos.dto.UserResponse;
import com.ravi.reportingwebsiteenfos.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/reports/users")
@RequiredArgsConstructor
public class UserController {

    private static final Set<String> SORTABLE_FIELDS = Set.of("id", "name", "status", "createdDate");

    private final UserService userService;

    @GetMapping
    public PagedResponse<UserResponse> getUsers(@PageableDefault(size = 10, sort = "id") Pageable pageable) {
        SortValidator.validate(pageable.getSort(), SORTABLE_FIELDS);
        return PagedResponse.of(userService.getUsers(pageable), UserResponse::from);
    }
}
