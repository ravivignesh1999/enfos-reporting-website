package com.ravi.reportingwebsiteenfos.model;

/** Known-good status values; {@link User#getStatus()} stays a String so out-of-set values can still pass through. */
public enum UserStatus {
    ACTIVE,
    INACTIVE,
    PENDING
}
