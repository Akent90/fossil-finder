package com.fossilfinder.controller;

import com.fossilfinder.model.FossilLocation;
import com.fossilfinder.model.SearchResult;
import com.fossilfinder.service.FossilDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Territory REST Controller
 * Handles all territory and fossil location endpoints
 */
@RestController
@RequestMapping("/api")
@Tag(name = "Territories", description = "Fossil location and territory data")
public class TerritoryController {

    private static final Logger log = LoggerFactory.getLogger(TerritoryController.class);

    private final FossilDataService fossilDataService;

    public TerritoryController(FossilDataService fossilDataService) {
        this.fossilDataService = fossilDataService;
    }

    @GetMapping("/territories")
    @Operation(summary = "Get all territories", description = "Returns metadata for all available territories")
    public ResponseEntity<Map<String, Object>> getAllTerritories() {
        try {
            Map<String, Object> territories = fossilDataService.getAllTerritories();
            return ResponseEntity.ok(territories);
        } catch (IOException e) {
            log.error("Error loading territories", e);
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/territory/{code}")
    @Operation(summary = "Get territory data", description = "Returns fossil locations for a specific territory")
    public ResponseEntity<List<FossilLocation>> getTerritoryData(
            @Parameter(description = "Territory code (e.g., OH, CA, BC)") @PathVariable String code) {
        try {
            List<FossilLocation> locations = fossilDataService.getTerritoryData(code);
            return ResponseEntity.ok(locations);
        } catch (IOException e) {
            log.error("Error loading territory data: {}", code, e);
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/search")
    @Operation(summary = "Search fossil locations", description = "Search across all or specific territories")
    public ResponseEntity<SearchResult> search(
            @Parameter(description = "Search query") @RequestParam String q,
            @Parameter(description = "Territory filter (optional)") @RequestParam(required = false) String territory) {

        if (q == null || q.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        SearchResult results = fossilDataService.search(q, territory);
        return ResponseEntity.ok(results);
    }
}