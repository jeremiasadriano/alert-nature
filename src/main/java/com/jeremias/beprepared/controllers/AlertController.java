package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.AlertRequest;
import com.jeremias.beprepared.dto.response.AlertResponse;
import com.jeremias.beprepared.mapper.BePreparedMapper;
import com.jeremias.beprepared.models.Alert;
import com.jeremias.beprepared.services.AlertService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alerts")
@Tag(name = "3. Alert Controller")
public class AlertController {
    private final ModelMapper modelMapper;
    private final AlertService alertService;
    private final BePreparedMapper bePreparedMapper;


    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> createAlert(@Valid @RequestBody AlertRequest alertRequest, @RequestParam Long cityId, @RequestParam Long provinceId) {
        var alert = this.modelMapper.map(alertRequest, Alert.class);
        String alertCreation = this.alertService.createAlert(alert, cityId, provinceId);
        return new ResponseEntity<>(alertCreation, HttpStatus.CREATED);
    }

    @GetMapping
//    @Secured("ROLE_ADMIN") //DIDN'T
//    @RolesAllowed("ROLE_ADMIN") //DIDN'T
//    @PostAuthorize("hasRole('ADMIN')") //WORKED
    public ResponseEntity<List<AlertResponse>> getAllAlerts(@RequestParam(name = "s", required = false) Boolean status) {
        var response = this.bePreparedMapper.mapAlert(this.alertService.getAllAlerts(status));
        return ResponseEntity.ok(response);

    }

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<List<AlertResponse>> getAlertsByCityId(@PathVariable Long cityId, @RequestParam(name = "s", required = false) Boolean status) {
        var response = this.bePreparedMapper.mapAlert(this.alertService.getAlertsByCityId(cityId, status));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<List<AlertResponse>> getAlertsByProvinceId(@PathVariable Long provinceId, @RequestParam(name = "s", required = false) Boolean status) {
        var response = this.bePreparedMapper.mapAlert(this.alertService.getAlertsByProvinceId(provinceId, status));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResponse> getAlertById(@PathVariable Long alertId) {
        var response = this.bePreparedMapper.mapAlert(this.alertService.getAlertById(alertId));
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{alertId}/active")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> activeAlert(@PathVariable Long alertId) {
        return ResponseEntity.ok(this.alertService.activeAlert(alertId));
    }
}
