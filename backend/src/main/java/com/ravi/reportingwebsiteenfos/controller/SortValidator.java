package com.ravi.reportingwebsiteenfos.controller;

import com.ravi.reportingwebsiteenfos.exception.InvalidSortFieldException;
import org.springframework.data.domain.Sort;

import java.util.Set;

final class SortValidator {

    private SortValidator() {
    }

    static void validate(Sort sort, Set<String> allowedFields) {
        for (Sort.Order order : sort) {
            if (!allowedFields.contains(order.getProperty())) {
                throw new InvalidSortFieldException(order.getProperty(), allowedFields);
            }
        }
    }
}
