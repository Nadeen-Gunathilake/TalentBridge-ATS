package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.ApplicationRecruiterViewDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationStatusUpdateDTO;
import com.noobdevs.talentbridge_ats.exception.ResourceNotFoundException;
import com.noobdevs.talentbridge_ats.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    // FR-U7 — candidate's own applications
    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications(Authentication authentication) {
        return ResponseEntity.ok(applicationService.getMyApplications(authentication.getName()));
    }

    // FR-U7 (single) — candidate viewing their own application
    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getMyApplicationById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(applicationService.getMyApplicationById(id, authentication.getName()));
    }

    // FR-U5 — apply to an open job (multipart: optional cover note + optional resume)
    @PostMapping(value = "/jobs/{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationResponseDTO> applyToJob(
            @PathVariable Long jobId,
            @RequestParam(required = false) String coverNote,
            @RequestParam(required = false) MultipartFile resume,
            Authentication authentication) {

        ApplicationResponseDTO created = applicationService.applyToJob(jobId, coverNote, resume, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // FR-U8 — withdraw (soft status change, ownership-checked in service)
    @PutMapping("/{id}/withdraw")
    public ResponseEntity<ApplicationResponseDTO> withdrawApplication(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(applicationService.withdrawApplication(id, authentication.getName()));
    }

    // FR-R6 — recruiter: applications for a specific job
    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<List<ApplicationRecruiterViewDTO>> getApplicationsForJob(@PathVariable Long jobId) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId));
    }

    // FR-R7 — recruiter: full detail on a single application
    @GetMapping("/recruiter/{id}")
    public ResponseEntity<ApplicationRecruiterViewDTO> getApplicationByIdForRecruiter(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationByIdForRecruiter(id));
    }

    // FR-R10 (basic) — advance status
    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationRecruiterViewDTO> changeStatus(@PathVariable Long id, @Valid @RequestBody ApplicationStatusUpdateDTO dto) {
        return ResponseEntity.ok(applicationService.changeStatus(id, dto.getStatus()));
    }

    // FR-R11 — recruiter downloads the candidate's resume
    @GetMapping("/{id}/resume")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long id) throws IOException {
        String resumePath = applicationService.getResumePath(id);
        if (resumePath == null) {
            throw new ResourceNotFoundException("No resume uploaded for this application");
        }

        Path path = Paths.get(resumePath);
        Resource resource = new UrlResource(path.toUri());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + path.getFileName() + "\"")
                .body(resource);
    }
}
