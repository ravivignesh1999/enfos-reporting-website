package com.ravi.reportingwebsiteenfos.exception;

import java.util.Set;

public class InvalidSortFieldException extends RuntimeException {

    public InvalidSortFieldException(String field, Set<String> allowedFields) {
        super("Cannot sort by '" + field + "'. Allowed fields: " + allowedFields);
    }
}
