package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.models.dto.response.ProvinceResponseDto;
import com.jeremias.beprepared.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;
    private final ModelMapper mapper;

    @GetMapping("/provinces")
    public ResponseEntity<List<ProvinceResponseDto>> getAllProvinces() {
        var response = provinceService.getAllProvinces();
        return ResponseEntity.ok(response.stream().map((e) -> mapper.map(e, ProvinceResponseDto.class)).toList());
    }
}
