package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.response.ProvinceResponse;
import com.jeremias.beprepared.services.ProvinceService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/provinces")
@RequiredArgsConstructor
public class ProvinceController {
    private final ProvinceService provinceService;
    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<ProvinceResponse>> getAllProvinces() {
        var response = provinceService.getAllProvinces();
        return ResponseEntity.ok(response.stream().map((e) -> this.mapper.map(e, ProvinceResponse.class)).toList());
    }

    @GetMapping("/{provinceId}")
    public ResponseEntity<ProvinceResponse> getProvinceById(@PathVariable Long provinceId) {
        return ResponseEntity.ok(this.mapper.map(provinceService.getProvinceById(provinceId), ProvinceResponse.class));
    }
}
