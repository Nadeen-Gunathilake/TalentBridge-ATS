package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.ApplicationRequestDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import com.noobdevs.talentbridge_ats.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getAllApplications(){
        return ResponseEntity.ok(applicationService.getAllApplications());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable Long id){
        return ResponseEntity.ok(applicationService.getApplicationById(id));
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(@Valid @RequestBody ApplicationRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationService.createApplication(dto));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id){
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/status/{id}")
    public ResponseEntity<ApplicationResponseDTO> changeStatus(@PathVariable Long id,@Valid ApplicationStatus status){
        return ResponseEntity.ok(applicationService.changeStatus(id,status));
    }
}
