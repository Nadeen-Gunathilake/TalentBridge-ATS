package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.CandidateRequestDTO;
import com.noobdevs.talentbridge_ats.dto.CandidateResponseDTO;
import com.noobdevs.talentbridge_ats.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/candidates")
@PreAuthorize("hasRole('CANDIDATE')")
@RequiredArgsConstructor
public class CandidateController {

    private final CandidateService candidateService;

    @GetMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> getCandidateById(@PathVariable Long id,Authentication authentication) {
        return ResponseEntity.ok(candidateService.getCandidateById(id, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CandidateResponseDTO> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateRequestDTO dto,Authentication authenticaton){
        String authenticatedEmail = authenticaton.getName();
        return ResponseEntity.ok(candidateService.updateCandidate(id, dto,authenticatedEmail));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id,Authentication authentication){
        candidateService.deleteCandidate(id, authentication.getName());
        return ResponseEntity.noContent().build();
    }

}
