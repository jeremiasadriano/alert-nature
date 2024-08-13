package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.response.CityResponse;
import com.jeremias.beprepared.services.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cities")
@RequiredArgsConstructor
@Tag(name = "6. City Controller")
public class CityController {
    private final CityService cityService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CityResponse>> getAllCities() {
        var response = cityService.getAllCities();
        return ResponseEntity.ok(response.stream().map((e) -> this.mapper.map(e, CityResponse.class)).toList());
    }

    @GetMapping("/{provincesId}")
    public ResponseEntity<List<CityResponse>> getCitiesByProvinceId(@PathVariable Long provincesId) {
        var response = cityService.getCitiesByProvinceId(provincesId);
        return ResponseEntity.ok(response.stream().map((e) -> this.mapper.map(e, CityResponse.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityResponse> getCityById(@PathVariable Long id) {
        return ResponseEntity.ok(this.mapper.map(cityService.getCityById(id), CityResponse.class));
    }

}
