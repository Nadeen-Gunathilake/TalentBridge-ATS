package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.RecruiterRequestDTO;
import com.noobdevs.talentbridge_ats.dto.RecruiterResponseDTO;
import com.noobdevs.talentbridge_ats.service.RecruiterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/recruiters")
@PreAuthorize("hasRole('RECRUITER')")
@RequiredArgsConstructor
public class RecruiterController {

    private final RecruiterService recruiterService;

    @PostMapping
    public ResponseEntity<RecruiterResponseDTO> createRecruiter(@RequestBody RecruiterRequestDTO dto) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(recruiterService.createRecruiter(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecruiterResponseDTO> getRecruiterById(@PathVariable Long id) {
        return ResponseEntity.ok(recruiterService.getRecruiterById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecruiterResponseDTO> updateRecruiter(@PathVariable Long id, @Valid @RequestBody RecruiterRequestDTO dto){
        return ResponseEntity.ok(recruiterService.updateRecruiter(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecruiter(@PathVariable Long id){
        recruiterService.deleteRecruiter(id);
        return ResponseEntity.noContent().build();
    }



}
