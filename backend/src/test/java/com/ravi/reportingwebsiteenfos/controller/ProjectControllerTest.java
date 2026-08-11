package com.ravi.reportingwebsiteenfos.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProjectControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsPaginatedProjects() throws Exception {
        mockMvc.perform(get("/api/reports/projects"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(10))
                .andExpect(jsonPath("$.totalElements").value(40));
    }

    @Test
    void sortingByAllowedFieldSucceeds() throws Exception {
        mockMvc.perform(get("/api/reports/projects").param("sort", "startDate"))
                .andExpect(status().isOk());
    }

    @Test
    void sortingByDisallowedFieldReturns400() throws Exception {
        mockMvc.perform(get("/api/reports/projects").param("sort", "owner"))
                .andExpect(status().isBadRequest());
    }
}
