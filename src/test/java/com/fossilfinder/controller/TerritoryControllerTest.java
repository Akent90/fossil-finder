package com.fossilfinder.controller;

import com.fossilfinder.model.FossilLocation;
import com.fossilfinder.service.FossilDataService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit tests for TerritoryController
 */
@WebMvcTest(TerritoryController.class)
class TerritoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FossilDataService fossilDataService;

    @Test
    void getAllTerritories_ReturnsMetadata() throws Exception {
        // Given
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("totalTerritories", 1);
        when(fossilDataService.getAllTerritories()).thenReturn(metadata);

        // When & Then
        mockMvc.perform(get("/api/territories"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalTerritories").value(1));
    }

    @Test
    void getTerritoryData_ValidCode_ReturnsLocations() throws Exception {
        // Given
        FossilLocation location = new FossilLocation();
        location.setLocation("Test Location");
        location.setCounty("Test County");

        when(fossilDataService.getTerritoryData("OH"))
                .thenReturn(Collections.singletonList(location));

        // When & Then
        mockMvc.perform(get("/api/territory/OH"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].location").value("Test Location"));
    }

    @Test
    void getTerritoryData_InvalidCode_ReturnsNotFound() throws Exception {
        // Given
        when(fossilDataService.getTerritoryData(anyString()))
                .thenThrow(new java.io.IOException("Not found"));

        // When & Then
        mockMvc.perform(get("/api/territory/XX"))
                .andExpect(status().isNotFound());
    }

    @Test
    void search_WithQuery_ReturnsResults() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/search")
                .param("q", "trilobite"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.query").value("trilobite"));
    }

    @Test
    void search_WithoutQuery_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/search"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void search_WithEmptyQuery_ReturnsBadRequest() throws Exception {
        // When & Then
        mockMvc.perform(get("/api/search")
                .param("q", ""))
                .andExpect(status().isBadRequest());
    }
}