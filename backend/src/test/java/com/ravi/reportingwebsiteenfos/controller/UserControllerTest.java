package com.ravi.reportingwebsiteenfos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void defaultsToTenRowsPerPage() throws Exception {
        mockMvc.perform(get("/api/reports/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.page").value(0))
                .andExpect(jsonPath("$.size").value(10))
                .andExpect(jsonPath("$.totalElements").value(40))
                .andExpect(jsonPath("$.totalPages").value(4));
    }

    @Test
    void sizeIsOverridable() throws Exception {
        mockMvc.perform(get("/api/reports/users").param("size", "5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(5))
                .andExpect(jsonPath("$.totalPages").value(8));
    }

    @Test
    void oversizedPageIsCappedAtMax() throws Exception {
        mockMvc.perform(get("/api/reports/users").param("size", "9999"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size").value(100));
    }

    @Test
    void sortingByAllowedFieldSucceeds() throws Exception {
        mockMvc.perform(get("/api/reports/users").param("sort", "name"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10));
    }

    @Test
    void sortingByDisallowedFieldReturns400() throws Exception {
        mockMvc.perform(get("/api/reports/users").param("sort", "email"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").value(containsString("email")));
    }

    @Test
    void unknownRouteReturns404NotServerError() throws Exception {
        mockMvc.perform(get("/api/reports/users/does-not-exist"))
                .andExpect(status().isNotFound());
    }
}
