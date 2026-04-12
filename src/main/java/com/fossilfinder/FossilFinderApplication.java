package com.fossilfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Fossil Finder - Spring Boot Application
 * North American fossil location database with REST API
 * 
 * @author A Kent
 * @version 1.0.0
 */
@SpringBootApplication
@EnableScheduling
public class FossilFinderApplication {

    public static void main(String[] args) {
        SpringApplication.run(FossilFinderApplication.class, args);
    }
}