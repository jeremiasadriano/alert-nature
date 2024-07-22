package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.AlertRequest;
import com.jeremias.beprepared.dto.response.AlertResponse;
import com.jeremias.beprepared.mapper.AlertModelMapper;
import com.jeremias.beprepared.models.Alert;
import com.jeremias.beprepared.services.AlertService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class AlertController {
    private final ModelMapper modelMapper;
    private final AlertService alertService;
    private final AlertModelMapper alertModelMapper;


    @PostMapping("/createAlert/")
    public ResponseEntity<String> createAlert(@RequestBody AlertRequest alertRequest, @RequestParam Long cityId, @RequestParam Long provinceId) {
        var alert = this.modelMapper.map(alertRequest, Alert.class);
        String alertCreation = this.alertService.createAlert(alert, cityId, provinceId);
        return new ResponseEntity<>(alertCreation, HttpStatus.CREATED);
    }

    @GetMapping("/alerts")
    public ResponseEntity<List<AlertResponse>> getAllAlerts() {
        var response = this.alertService.getAllAlerts().stream().map(this.alertModelMapper::map).toList();
        return ResponseEntity.ok(response);
    }
}
