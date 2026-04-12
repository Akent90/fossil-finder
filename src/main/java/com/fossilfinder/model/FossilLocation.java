package com.fossilfinder.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Fossil Location Data Model
 * Represents a single fossil hunting location
 */
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

    // Default constructor
    public FossilLocation() {
    }

    // Getters and Setters
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getDirections() {
        return directions;
    }

    public void setDirections(String directions) {
        this.directions = directions;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getFormation() {
        return formation;
    }

    public void setFormation(String formation) {
        this.formation = formation;
    }

    public String getFossils() {
        return fossils;
    }

    public void setFossils(String fossils) {
        this.fossils = fossils;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getLatLong() {
        return latLong;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }
}