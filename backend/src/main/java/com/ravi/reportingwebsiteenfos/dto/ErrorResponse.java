package com.ravi.reportingwebsiteenfos.dto;

import lombok.Getter;

import java.time.Instant;

@Getter
public class ErrorResponse {

    private final Instant timestamp;
    private final int status;
    private final String message;
    private final String path;

    public ErrorResponse(int status, String message, String path) {
        this.timestamp = Instant.now();
        this.status = status;
        this.message = message;
        this.path = path;
    }
}
