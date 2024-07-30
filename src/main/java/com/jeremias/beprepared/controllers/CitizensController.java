package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.CitizensRequest;
import com.jeremias.beprepared.dto.response.CitizensResponse;
import com.jeremias.beprepared.mapper.BePreparedMapper;
import com.jeremias.beprepared.models.Citizens;
import com.jeremias.beprepared.services.CitizensService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/citizens")
@RequiredArgsConstructor
public class CitizensController {
    private final CitizensService citizensService;
    private final ModelMapper modelMapper;
    private final BePreparedMapper bePreparedMapper;

    @PostMapping("/{cityId}")
    public ResponseEntity<String> createCitizens(@RequestBody CitizensRequest citizensRequest, @PathVariable("cityId") Long cityId) {
        Citizens citizens = this.modelMapper.map(citizensRequest, Citizens.class);
        return new ResponseEntity<>(this.citizensService.createCitizens(citizens, cityId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CitizensResponse>> getAllCitizens() {
        List<CitizensResponse> citizens = this.bePreparedMapper.map(this.citizensService.getAllCitizens());
        return ResponseEntity.ok(citizens);
    }
}
