package com.ravi.reportingwebsiteenfos.model;

/** Known-good status values; {@link Project#getStatus()} stays a String so out-of-set values can still pass through. */
public enum ProjectStatus {
    ACTIVE,
    ON_HOLD,
    COMPLETED,
    CANCELLED
}
