package com.fossilfinder.model;

import java.util.List;

/**
 * Search Result Wrapper
 * Contains search query and matching results
 */
public class SearchResult {
    private String query;
    private Integer count;
    private List<FossilLocationResult> results;

    public SearchResult() {
    }

    public SearchResult(String query, Integer count, List<FossilLocationResult> results) {
        this.query = query;
        this.count = count;
        this.results = results;
    }

    // Getters and Setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public List<FossilLocationResult> getResults() {
        return results;
    }

    public void setResults(List<FossilLocationResult> results) {
        this.results = results;
    }

    /**
     * Individual search result with territory context
     */
    public static class FossilLocationResult {
        private String territory;
        private FossilLocation data;

        public FossilLocationResult() {
        }

        public FossilLocationResult(String territory, FossilLocation data) {
            this.territory = territory;
            this.data = data;
        }

        // Getters and Setters
        public String getTerritory() {
            return territory;
        }

        public void setTerritory(String territory) {
            this.territory = territory;
        }

        public FossilLocation getData() {
            return data;
        }

        public void setData(FossilLocation data) {
            this.data = data;
        }
    }
}