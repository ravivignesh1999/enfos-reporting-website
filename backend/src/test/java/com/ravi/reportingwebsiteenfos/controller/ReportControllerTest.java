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
class ReportControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void returnsAllThreeReportsWithLiveRowCounts() throws Exception {
        mockMvc.perform(get("/api/reports"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].id").value("users"))
                .andExpect(jsonPath("$[0].rowCount").value(40))
                .andExpect(jsonPath("$[0].lastUpdated").exists())
                .andExpect(jsonPath("$[1].id").value("departments"))
                .andExpect(jsonPath("$[1].rowCount").value(40))
                .andExpect(jsonPath("$[2].id").value("projects"))
                .andExpect(jsonPath("$[2].rowCount").value(40));
    }
}
