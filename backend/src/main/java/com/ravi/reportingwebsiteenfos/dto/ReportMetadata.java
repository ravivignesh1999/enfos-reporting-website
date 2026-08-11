package com.ravi.reportingwebsiteenfos.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

/** Catalog entry for GET /api/reports. */
@Getter
@Builder
@AllArgsConstructor
public class ReportMetadata {

    private String id; // matches the report's URL slug, e.g. "users"

    private String name;
    private String description;
    private long rowCount;
    private Instant lastUpdated;
}
