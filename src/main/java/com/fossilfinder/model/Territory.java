package com.fossilfinder.model;

/**
 * Territory Metadata Model
 * Represents metadata about a territory (state/province)
 */
public class Territory {
    private String code;
    private String name;
    private String country;
    private String dataFile;
    private Integer locationCount;

    public Territory() {
    }

    public Territory(String code, String name, String country, String dataFile, Integer locationCount) {
        this.code = code;
        this.name = name;
        this.country = country;
        this.dataFile = dataFile;
        this.locationCount = locationCount;
    }

    // Getters and Setters
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getDataFile() {
        return dataFile;
    }

    public void setDataFile(String dataFile) {
        this.dataFile = dataFile;
    }

    public Integer getLocationCount() {
        return locationCount;
    }

    public void setLocationCount(Integer locationCount) {
        this.locationCount = locationCount;
    }
}