package com.ravi.reportingwebsiteenfos.controller;

import com.ravi.reportingwebsiteenfos.dto.ReportMetadata;
import com.ravi.reportingwebsiteenfos.service.ReportCatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportCatalogService reportCatalogService;

    @GetMapping
    public List<ReportMetadata> getReports() {
        return reportCatalogService.getAvailableReports();
    }
}
