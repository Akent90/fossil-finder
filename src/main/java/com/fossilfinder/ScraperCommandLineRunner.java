package com.fossilfinder;

import com.fossilfinder.service.ScraperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class ScraperCommandLineRunner implements CommandLineRunner {
    
    private static final Logger logger = LoggerFactory.getLogger(ScraperCommandLineRunner.class);
    
    private final ScraperService scraperService;
    
    public ScraperCommandLineRunner(ScraperService scraperService) {
        this.scraperService = scraperService;
    }
    
    @Override
    public void run(String... args) throws Exception {
        // Only run scraper if --scraper argument is provided
        if (args.length > 0 && Arrays.asList(args).contains("--scraper")) {
            logger.info("==================================================");
            logger.info("Starting Fossil Data Scraper");
            logger.info("==================================================");
            
            scraperService.scrapeAllTerritories();
            
            logger.info("==================================================");
            logger.info("Scraping complete!");
            logger.info("==================================================");
            
            // Exit after scraping
            System.exit(0);
        }
    }
}
