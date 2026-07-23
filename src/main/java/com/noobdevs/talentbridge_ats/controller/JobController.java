package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/jobs")
public class JobController {

        private final JobService jobService;

        public JobController(JobService jobService) {
            this.jobService = jobService;
        }

        @GetMapping
        public ResponseEntity<List<JobResponseDTO>> getAllJobs(){
            return ResponseEntity.ok(jobService.getAllJobs());
        }

        @GetMapping("/{id}")
        public ResponseEntity<JobResponseDTO> getJobById(@PathVariable Long id){
            return ResponseEntity.ok(jobService.getJobById(id));
        }

        @PostMapping
        public ResponseEntity<JobResponseDTO> createJob(@Valid @RequestBody JobRequestDTO dto){
            return ResponseEntity.status(HttpStatus.CREATED).body(jobService.createJob(dto));
        }

        @PutMapping("/{id}")
        public ResponseEntity<JobResponseDTO> updateJob(@PathVariable Long id, @Valid @RequestBody JobRequestDTO dto){
            return ResponseEntity.ok(jobService.updateJob(id, dto));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteJob(@PathVariable Long id){
            jobService.deleteJob(id);
            return ResponseEntity.noContent().build();
        }

        @PutMapping("/{id}")
        public ResponseEntity<JobResponseDTO> changeStatus(@PathVariable Long id,@Valid JobStatus status){
            return ResponseEntity.ok(jobService.changeStatus(id,status));
        }
}
