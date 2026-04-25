package com.fossilfinder.controller;

import com.fossilfinder.model.weather.WeatherDTO;
import com.fossilfinder.service.WeatherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/weather")
@Tag(name = "Weather", description = "Weather information for fossil hunting locations")
@CrossOrigin(origins = "*")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping
    @Operation(summary = "Get current weather for a location")
    public ResponseEntity<WeatherDTO> getWeather(
            @Parameter(description = "County name") @RequestParam String county,
            @Parameter(description = "State name or code") @RequestParam String state
    ) {
        WeatherDTO weather = weatherService.getWeatherByCounty(county, state);
        return ResponseEntity.ok(weather);
    }
}
