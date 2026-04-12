package com.fossilfinder.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fossilfinder.model.FossilLocation;
import com.fossilfinder.model.SearchResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Fossil Data Service
 * Handles reading and searching fossil location data
 */
@Service
public class FossilDataService {

    private static final Logger log = LoggerFactory.getLogger(FossilDataService.class);

    private final ObjectMapper objectMapper;
    private final ResourceLoader resourceLoader;

    @Value("${fossil.data.directory:classpath:data/}")
    private String dataDirectory;

    public FossilDataService(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
        this.objectMapper = objectMapper;
        this.resourceLoader = resourceLoader;
    }

    /**
     * Get all territories metadata
     */
    public Map<String, Object> getAllTerritories() throws IOException {
        Resource resource = resourceLoader.getResource(dataDirectory + "territories.json");
        try (InputStream is = resource.getInputStream()) {
            return objectMapper.readValue(is, new TypeReference<Map<String, Object>>() {
            });
        }
    }

    /**
     * Get fossil locations for a specific territory
     */
    public List<FossilLocation> getTerritoryData(String territoryCode) throws IOException {
        String upperCode = territoryCode.toUpperCase();
        Resource resource = resourceLoader.getResource(dataDirectory + upperCode + ".json");

        if (!resource.exists()) {
            throw new IOException("Territory data not found: " + upperCode);
        }

        try (InputStream is = resource.getInputStream()) {
            return objectMapper.readValue(is, new TypeReference<List<FossilLocation>>() {
            });
        }
    }

    /**
     * Search across territories
     */
    public SearchResult search(String query, String territoryFilter) {
        List<SearchResult.FossilLocationResult> results = new ArrayList<>();
        String lowerQuery = query.toLowerCase();

        try {
            Map<String, Object> metadata = getAllTerritories();
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> territories = (List<Map<String, Object>>) metadata.get("territories");

            for (Map<String, Object> territory : territories) {
                String code = (String) territory.get("code");

                // Skip if territory filter is set and doesn't match
                if (territoryFilter != null && !territoryFilter.isEmpty() && !code.equals(territoryFilter)) {
                    continue;
                }

                try {
                    List<FossilLocation> locations = getTerritoryData(code);

                    for (FossilLocation location : locations) {
                        if (matchesQuery(location, lowerQuery)) {
                            results.add(new SearchResult.FossilLocationResult(code, location));
                        }
                    }
                } catch (IOException e) {
                    log.warn("Could not load data for territory: {}", code);
                }
            }
        } catch (IOException e) {
            log.error("Error searching territories", e);
        }

        return new SearchResult(query, results.size(), results);
    }

    private boolean matchesQuery(FossilLocation location, String query) {
        String searchText = String.join(" ",
                location.getLocation() != null ? location.getLocation() : "",
                location.getCounty() != null ? location.getCounty() : "",
                location.getAge() != null ? location.getAge() : "",
                location.getFormation() != null ? location.getFormation() : "",
                location.getFossils() != null ? location.getFossils() : "",
                location.getComments() != null ? location.getComments() : "").toLowerCase();

        return searchText.contains(query);
    }
}