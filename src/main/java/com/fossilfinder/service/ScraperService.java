package com.fossilfinder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fossilfinder.model.FossilLocation;
import com.fossilfinder.model.Territory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.io.File;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ScraperService {

    private static final Logger logger = LoggerFactory.getLogger(ScraperService.class);
    private static final String BASE_URL = "http://www.fossilspot.com";
    private static final String DATA_DIR = "src/main/resources/data";
    private final ObjectMapper objectMapper = new ObjectMapper();

    // Disable SSL certificate validation for scraping
    static {
        try {
            TrustManager[] trustAllCerts = new TrustManager[] {
                    new X509TrustManager() {
                        public X509Certificate[] getAcceptedIssuers() {
                            return null;
                        }

                        public void checkClientTrusted(X509Certificate[] certs, String authType) {
                        }

                        public void checkServerTrusted(X509Certificate[] certs, String authType) {
                        }
                    }
            };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = (hostname, session) -> true;
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

            logger.info("SSL certificate validation disabled for scraping");
        } catch (Exception e) {
            logger.error("Failed to disable SSL verification", e);
        }
    }

    // Territory metadata (code, name, country)
    private static class TerritoryInfo {
        String code;
        String name;
        String country;

        TerritoryInfo(String code, String name, String country) {
            this.code = code;
            this.name = name;
            this.country = country;
        }
    }

    // All territories to scrape
    private static final List<TerritoryInfo> TERRITORY_LIST = List.of(
            // US States
            new TerritoryInfo("AL", "Alabama", "United States"),
            new TerritoryInfo("AK", "Alaska", "United States"),
            new TerritoryInfo("AZ", "Arizona", "United States"),
            new TerritoryInfo("AR", "Arkansas", "United States"),
            new TerritoryInfo("CA", "California", "United States"),
            new TerritoryInfo("CO", "Colorado", "United States"),
            new TerritoryInfo("CT", "Connecticut", "United States"),
            new TerritoryInfo("DE", "Delaware", "United States"),
            new TerritoryInfo("FL", "Florida", "United States"),
            new TerritoryInfo("GA", "Georgia", "United States"),
            new TerritoryInfo("HI", "Hawaii", "United States"),
            new TerritoryInfo("ID", "Idaho", "United States"),
            new TerritoryInfo("IL", "Illinois", "United States"),
            new TerritoryInfo("IN", "Indiana", "United States"),
            new TerritoryInfo("IA", "Iowa", "United States"),
            new TerritoryInfo("KS", "Kansas", "United States"),
            new TerritoryInfo("KY", "Kentucky", "United States"),
            new TerritoryInfo("LA", "Louisiana", "United States"),
            new TerritoryInfo("ME", "Maine", "United States"),
            new TerritoryInfo("MD", "Maryland", "United States"),
            new TerritoryInfo("MA", "Massachusetts", "United States"),
            new TerritoryInfo("MI", "Michigan", "United States"),
            new TerritoryInfo("MN", "Minnesota", "United States"),
            new TerritoryInfo("MS", "Mississippi", "United States"),
            new TerritoryInfo("MO", "Missouri", "United States"),
            new TerritoryInfo("MT", "Montana", "United States"),
            new TerritoryInfo("NE", "Nebraska", "United States"),
            new TerritoryInfo("NV", "Nevada", "United States"),
            new TerritoryInfo("NH", "New Hampshire", "United States"),
            new TerritoryInfo("NJ", "New Jersey", "United States"),
            new TerritoryInfo("NM", "New Mexico", "United States"),
            new TerritoryInfo("NY", "New York", "United States"),
            new TerritoryInfo("NC", "North Carolina", "United States"),
            new TerritoryInfo("ND", "North Dakota", "United States"),
            new TerritoryInfo("OH", "Ohio", "United States"),
            new TerritoryInfo("OK", "Oklahoma", "United States"),
            new TerritoryInfo("OR", "Oregon", "United States"),
            new TerritoryInfo("PA", "Pennsylvania", "United States"),
            new TerritoryInfo("RI", "Rhode Island", "United States"),
            new TerritoryInfo("SC", "South Carolina", "United States"),
            new TerritoryInfo("SD", "South Dakota", "United States"),
            new TerritoryInfo("TN", "Tennessee", "United States"),
            new TerritoryInfo("TX", "Texas", "United States"),
            new TerritoryInfo("UT", "Utah", "United States"),
            new TerritoryInfo("VT", "Vermont", "United States"),
            new TerritoryInfo("VA", "Virginia", "United States"),
            new TerritoryInfo("WA", "Washington", "United States"),
            new TerritoryInfo("WV", "West Virginia", "United States"),
            new TerritoryInfo("WI", "Wisconsin", "United States"),
            new TerritoryInfo("WY", "Wyoming", "United States"),
            new TerritoryInfo("DC", "District of Columbia", "United States"),

            // Canadian Provinces
            new TerritoryInfo("AB", "Alberta", "Canada"),
            new TerritoryInfo("BC", "British Columbia", "Canada"),
            new TerritoryInfo("MB", "Manitoba", "Canada"),
            new TerritoryInfo("NB", "New Brunswick", "Canada"),
            new TerritoryInfo("NL", "Newfoundland and Labrador", "Canada"),
            new TerritoryInfo("NT", "Northwest Territories", "Canada"),
            new TerritoryInfo("NS", "Nova Scotia", "Canada"),
            new TerritoryInfo("NU", "Nunavut", "Canada"),
            new TerritoryInfo("ON", "Ontario", "Canada"),
            new TerritoryInfo("PE", "Prince Edward Island", "Canada"),
            new TerritoryInfo("QC", "Quebec", "Canada"),
            new TerritoryInfo("SK", "Saskatchewan", "Canada"),
            new TerritoryInfo("YT", "Yukon", "Canada"),

            // Other
            new TerritoryInfo("BA", "Bahamas", "Bahamas"));

    public void scrapeAllTerritories() throws IOException {
        logger.info("Starting to scrape {} territories", TERRITORY_LIST.size());

        // Create data directory if it doesn't exist
        File dataDir = new File(DATA_DIR);
        if (!dataDir.exists()) {
            dataDir.mkdirs();
        }

        List<Territory> territories = new ArrayList<>();
        int count = 0;

        for (TerritoryInfo info : TERRITORY_LIST) {
            count++;
            logger.info("[{}/{}] Scraping {}...", count, TERRITORY_LIST.size(), info.name);

            try {
                List<FossilLocation> locations = scrapeTerritory(info);
                saveLocations(info.code, locations);

                // Create territory metadata
                Territory territory = new Territory();
                territory.setCode(info.code);
                territory.setName(info.name);
                territory.setCountry(info.country);
                territory.setDataFile(info.code + ".json");
                territory.setLocationCount(locations.size());
                territories.add(territory);

                logger.info("  ✓ Found {} locations", locations.size());

                // Be polite - wait 1 second between requests
                Thread.sleep(1000);
            } catch (Exception e) {
                logger.error("  ✗ Error scraping {}: {}", info.name, e.getMessage());

                // Still add territory with 0 locations
                Territory territory = new Territory();
                territory.setCode(info.code);
                territory.setName(info.name);
                territory.setCountry(info.country);
                territory.setDataFile(info.code + ".json");
                territory.setLocationCount(0);
                territories.add(territory);
            }
        }

        // Save territories metadata
        saveTerritories(territories);
        logger.info("Scraping complete! Scraped {} territories", count);
    }

    private List<FossilLocation> scrapeTerritory(TerritoryInfo info) throws IOException {
        List<FossilLocation> locations = new ArrayList<>();

        // Build URL using 2-letter code: http://www.fossilspot.com/STATES/OH.HTM
        String url = BASE_URL + "/STATES/" + info.code + ".HTM";

        logger.debug("Fetching: {}", url);
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0")
                .timeout(10000)
                .get();

        // Find all location rows in the table
        Elements rows = doc.select("table tr");

        for (Element row : rows) {
            try {
                FossilLocation location = parseLocationRow(row, info);
                if (location != null && location.getLocation() != null && !location.getLocation().isEmpty()) {
                    locations.add(location);
                }
            } catch (Exception e) {
                logger.debug("Could not parse row: {}", e.getMessage());
            }
        }

        return locations;
    }

    private FossilLocation parseLocationRow(Element row, TerritoryInfo info) {
        Elements cells = row.select("td");
        if (cells.size() < 2) {
            return null; // Header row or invalid
        }

        FossilLocation location = new FossilLocation();

        // CORRECT ORDER: Location, County, State/Province, Directions, Age, Formation,
        // Fossils, Comments, Reference, Lat/Long
        if (cells.size() > 0)
            location.setLocation(cells.get(0).text().trim());
        if (cells.size() > 1)
            location.setCounty(cells.get(1).text().trim());
        // Skip index 2 (State/Province) - already set this from TerritoryInfo
        if (cells.size() > 3)
            location.setDirections(cells.get(3).text().trim());
        if (cells.size() > 4)
            location.setAge(cells.get(4).text().trim());
        if (cells.size() > 5)
            location.setFormation(cells.get(5).text().trim());
        if (cells.size() > 6)
            location.setFossils(cells.get(6).text().trim());
        if (cells.size() > 7)
            location.setComments(cells.get(7).text().trim());
        if (cells.size() > 8)
            location.setReference(cells.get(8).text().trim());
        if (cells.size() > 9)
            location.setLatLong(cells.get(9).text().trim());

        // Set state from territory info
        location.setState(info.name);

        return location;
    }

    private void saveLocations(String code, List<FossilLocation> locations) throws IOException {
        File file = new File(DATA_DIR + "/" + code + ".json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, locations);
    }

    private void saveTerritories(List<Territory> territories) throws IOException {
        File file = new File(DATA_DIR + "/territories.json");
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, territories);
    }
}
