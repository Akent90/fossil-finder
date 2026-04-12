package com.fossilfinder.model;

import lombok.Data;
import lombok.AllArgsConstructor;

import java.util.List;

/**
 * Search Result Wrapper
 */
@Data
@AllArgsConstructor
public class SearchResult {
    private String query;
    private Integer count;
    private List<FossilLocationResult> results;

    @Data
    @AllArgsConstructor
    public static class FossilLocationResult {
        private String territory;
        private FossilLocation data;
    }
}