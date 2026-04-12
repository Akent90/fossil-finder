package com.fossilfinder.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Fossil Location Data Model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FossilLocation {
    private String location;
    private String county;
    private String state;
    private String directions;
    private String age;
    private String formation;
    private String fossils;
    private String comments;
    private String reference;
    private String latLong;
}