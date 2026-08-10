package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.*;
import com.noobdevs.talentbridge_ats.dto.*;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import com.noobdevs.talentbridge_ats.exception.ResourceNotFoundException;
import com.noobdevs.talentbridge_ats.service.ApplicationService;
import jakarta.validation.Valid;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
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

    @GetMapping
    public ResponseEntity<List<ApplicationResponseDTO>> getMyApplications(Authentication authentication) {
        return ResponseEntity.ok(applicationService.getMyApplications(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> getMyApplicationById(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(applicationService.getMyApplicationById(id, authentication.getName()));
    }

    @PostMapping(value = "/jobs/{jobId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApplicationResponseDTO> applyToJob(
            @PathVariable Long jobId,
            @RequestParam(required = false) String coverNote,
            @RequestParam(required = false) MultipartFile resume,
            Authentication authentication) {

        ApplicationResponseDTO created = applicationService.applyToJob(jobId, coverNote, resume, authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<ApplicationResponseDTO> withdrawApplication(@PathVariable Long id, Authentication authentication) {
        return ResponseEntity.ok(applicationService.withdrawApplication(id, authentication.getName()));
    }

    @GetMapping("/jobs/{jobId}")
    public ResponseEntity<Page<ApplicationRecruiterViewDTO>> getApplicationsForJob(
            @PathVariable Long jobId,
            @RequestParam(required = false) ApplicationStatus status,
            @PageableDefault(size = 10, sort = "appliedAt") Pageable pageable) {
        return ResponseEntity.ok(applicationService.getApplicationsForJob(jobId, status, pageable));
    }

    @GetMapping("/recruiter/{id}")
    public ResponseEntity<ApplicationRecruiterViewDTO> getApplicationByIdForRecruiter(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getApplicationByIdForRecruiter(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApplicationRecruiterViewDTO> changeStatus(@PathVariable Long id, @Valid @RequestBody ApplicationStatusUpdateDTO dto) {
        return ResponseEntity.ok(applicationService.changeStatus(id, dto.getStatus()));
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<ApplicationRecruiterViewDTO> rateApplication(@PathVariable Long id, @Valid @RequestBody ApplicationRatingUpdateDTO dto) {
        return ResponseEntity.ok(applicationService.rateApplication(id, dto.getRating()));
    }

    @PostMapping("/{id}/notes")
    public ResponseEntity<ApplicationNoteResponseDTO> addNote(
            @PathVariable Long id,
            @Valid @RequestBody ApplicationNoteRequestDTO dto,
            Authentication authentication) {
        ApplicationNoteResponseDTO created = applicationService.addNote(id, dto.getContent(), authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}/notes")
    public ResponseEntity<List<ApplicationNoteResponseDTO>> getNotes(@PathVariable Long id) {
        return ResponseEntity.ok(applicationService.getNotes(id));
    }

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
