package com.fossilfinder.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

/**
 * Territory Metadata Model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Territory {
    private String code;
    private String name;
    private String country;
    private String dataFile;
    private Integer locationCount;
}