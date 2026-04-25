package com.fossilfinder.service;

import com.fossilfinder.model.weather.WeatherDTO;
import com.fossilfinder.model.weather.WeatherResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${openweather.api.key}")
    private String apiKey;

    @Value("${openweather.api.base-url}")
    private String baseUrl;

    private final RestTemplate restTemplate;

    public WeatherService() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * Get current weather for a location by county and state
     * Uses geocoding to convert county name to coordinates
     */
    public WeatherDTO getWeatherByCounty(String county, String state) {
        try {
            // For now, we'll use the county name directly
            // In a production app, you'd geocode this first
            // For simplicity, we'll use a default location for the state
            
            // Get coordinates for the location
            double[] coords = geocodeLocation(county, state);
            
            // Call OpenWeather API
            String url = UriComponentsBuilder.fromHttpUrl(baseUrl + "/weather")
                    .queryParam("lat", coords[0])
                    .queryParam("lon", coords[1])
                    .queryParam("appid", apiKey)
                    .queryParam("units", "imperial")
                    .build()
                    .toUriString();

            logger.info("Calling OpenWeather API for: {}, {}", county, state);
            
            WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);
            
            return mapToDTO(response, county + ", " + state);
            
        } catch (Exception e) {
            logger.error("Error fetching weather data: {}", e.getMessage());
            throw new RuntimeException("Failed to fetch weather data", e);
        }
    }

    /**
     * Simple geocoding - maps state to approximate center coordinates
     * TODO: Implement proper geocoding API call for accurate county coordinates
     */
    private double[] geocodeLocation(String county, String state) {
        // Simplified: Return approximate state center coordinates
        // In production, use OpenWeather Geocoding API
        
        switch (state.toUpperCase()) {
            case "OHIO":
            case "OH":
                return new double[]{40.4173, -82.9071}; // Columbus, OH
            case "CALIFORNIA":
            case "CA":
                return new double[]{36.7783, -119.4179}; // CA center
            case "TEXAS":
            case "TX":
                return new double[]{31.9686, -99.9018}; // TX center
            case "FLORIDA":
            case "FL":
                return new double[]{27.9944, -81.7603}; // FL center
            // Add more states as needed
            default:
                logger.warn("Unknown state: {}, using default coordinates", state);
                return new double[]{39.8283, -98.5795}; // Geographic center of US
        }
    }

    /**
     * Map OpenWeather API response to our simplified DTO
     */
    private WeatherDTO mapToDTO(WeatherResponse response, String location) {
        WeatherDTO dto = new WeatherDTO();
        dto.setLocation(location);
        
        if (response.getMain() != null) {
            dto.setTemperature(response.getMain().getTemp());
            dto.setFeelsLike(response.getMain().getFeelsLike());
            dto.setHumidity(response.getMain().getHumidity());
        }
        
        if (response.getWind() != null) {
            dto.setWindSpeed(response.getWind().getSpeed());
        }
        
        if (response.getWeather() != null && !response.getWeather().isEmpty()) {
            WeatherResponse.Weather weather = response.getWeather().get(0);
            dto.setCondition(weather.getMain());
            dto.setDescription(weather.getDescription());
            dto.setIcon(weather.getIcon());
        }
        
        return dto;
    }
}
