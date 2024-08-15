package com.jeremias.beprepared.controllers;

import com.jeremias.beprepared.dto.request.CitizensRequest;
import com.jeremias.beprepared.dto.response.CitizensResponse;
import com.jeremias.beprepared.mapper.BePreparedMapper;
import com.jeremias.beprepared.models.Citizens;
import com.jeremias.beprepared.services.CitizensService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/citizens")
@RequiredArgsConstructor
@Tag(name = "4. Citizens Controller")
public class CitizensController {
    private final CitizensService citizensService;
    private final BePreparedMapper bePreparedMapper;

    @PostMapping("/{cityId}")
    public ResponseEntity<String> createCitizens(@Valid @RequestBody CitizensRequest citizensRequest, @PathVariable("cityId") Long cityId) {
        Citizens citizens = this.bePreparedMapper.mapCitizens(citizensRequest);
        return new ResponseEntity<>(this.citizensService.createCitizens(citizens, cityId), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<CitizensResponse>> getAllCitizens() {
        return ResponseEntity.ok(this.bePreparedMapper.mapCitizens(this.citizensService.getAllCitizens()));
    }

    @GetMapping("/cities/{cityId}")
    public ResponseEntity<List<CitizensResponse>> getAllCitizensByCityId(@PathVariable Long cityId) {
        return ResponseEntity.ok(this.bePreparedMapper.mapCitizens(this.citizensService.getAllCitizensByCityId(cityId)));
    }

    @GetMapping("/provinces/{provinceId}")
    public ResponseEntity<List<CitizensResponse>> getAllCitizensByProvinceId(@PathVariable Long provinceId) {
        return ResponseEntity.ok(this.bePreparedMapper.mapCitizens(this.citizensService.getAllCitizensByProvinceId(provinceId)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CitizensResponse> getCitizenById(@PathVariable Long id) {
        return ResponseEntity.ok(this.bePreparedMapper.mapCitizens(this.citizensService.getCitizenById(id)));
    }

    @PatchMapping("/verify")
    public ResponseEntity<String> verifyAccount(@RequestParam String otp) {
        return ResponseEntity.ok(this.citizensService.verifyAccount(otp));
    }

    @PatchMapping("/otp/renew")
    public ResponseEntity<String> renewOtp(@RequestParam(name = "device") String phone) {
        return new ResponseEntity<>(this.citizensService.renewOtp(phone), HttpStatus.ACCEPTED);
    }

    @PatchMapping("/delete/{device}")
    public ResponseEntity<String> deleteAccount(@PathVariable(name = "device") String phone) {
        return new ResponseEntity<>(this.citizensService.deleteAccount(phone), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/delete/{device}/confirmation")
    public ResponseEntity<String> confirmAccountDeletion(@RequestParam String otp, @PathVariable(name = "device") String phone) {
        this.citizensService.confirmAccountDeletion(otp, phone);
        return new ResponseEntity<>("Account deleted, we'll miss you", HttpStatus.NO_CONTENT);
    }
}
