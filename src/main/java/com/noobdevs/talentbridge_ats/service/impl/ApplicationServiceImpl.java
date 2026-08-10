package com.noobdevs.talentbridge_ats.service.impl;

import com.noobdevs.talentbridge_ats.dto.ApplicationNoteResponseDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationRecruiterViewDTO;
import com.noobdevs.talentbridge_ats.dto.ApplicationResponseDTO;
import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.exception.ResourceNotFoundException;
import com.noobdevs.talentbridge_ats.mapper.ApplicationMapper;
import com.noobdevs.talentbridge_ats.models.Application;
import com.noobdevs.talentbridge_ats.models.ApplicationNote;
import com.noobdevs.talentbridge_ats.models.Candidate;
import com.noobdevs.talentbridge_ats.models.Job;
import com.noobdevs.talentbridge_ats.models.Recruiter;
import com.noobdevs.talentbridge_ats.repository.ApplicationNoteRepository;
import com.noobdevs.talentbridge_ats.repository.ApplicationRepository;
import com.noobdevs.talentbridge_ats.repository.CandidateRepository;
import com.noobdevs.talentbridge_ats.repository.JobRepository;
import com.noobdevs.talentbridge_ats.repository.RecruiterRepository;
import com.noobdevs.talentbridge_ats.service.ApplicationPipeline;
import com.noobdevs.talentbridge_ats.service.ApplicationService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;
    private final JobRepository jobRepository;
    private final CandidateRepository candidateRepository;
    private final RecruiterRepository recruiterRepository;
    private final ApplicationNoteRepository applicationNoteRepository;
    private final ApplicationPipeline applicationPipeline;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository,
                                  ApplicationMapper applicationMapper,
                                  JobRepository jobRepository,
                                  CandidateRepository candidateRepository,
                                  RecruiterRepository recruiterRepository,
                                  ApplicationNoteRepository applicationNoteRepository,
                                  ApplicationPipeline applicationPipeline) {
        this.applicationRepository = applicationRepository;
        this.applicationMapper = applicationMapper;
        this.jobRepository = jobRepository;
        this.candidateRepository = candidateRepository;
        this.recruiterRepository = recruiterRepository;
        this.applicationNoteRepository = applicationNoteRepository;
        this.applicationPipeline = applicationPipeline;
    }

    @Override
    public List<ApplicationResponseDTO> getMyApplications(String candidateEmail) {
        Candidate candidate = candidateRepository.findByEmail(candidateEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

        return applicationRepository.findByCandidateId(candidate.getId()).stream()
                .map(applicationMapper::toResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ApplicationResponseDTO getMyApplicationById(Long id, String candidateEmail) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (!application.getCandidate().getEmail().equals(candidateEmail)) {
            throw new AccessDeniedException("You are not authorized to view this application");
        }

        return applicationMapper.toResponseDTO(application);
    }

    @Override
    public Page<ApplicationRecruiterViewDTO> getApplicationsForJob(Long jobId, ApplicationStatus status, Pageable pageable) {
        if (!jobRepository.existsById(jobId)) {
            throw new ResourceNotFoundException("Job not found with id: " + jobId);
        }

        Page<Application> applications = (status != null)
                ? applicationRepository.findByJobIdAndStatus(jobId, status, pageable)
                : applicationRepository.findByJobId(jobId, pageable);

        return applications.map(applicationMapper::toRecruiterViewDTO);
    }

    @Override
    public ApplicationRecruiterViewDTO getApplicationByIdForRecruiter(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return applicationMapper.toRecruiterViewDTO(application);
    }

    @Override
    public ApplicationResponseDTO applyToJob(Long jobId, String coverNote, MultipartFile resume, String candidateEmail) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + jobId));

        if (job.getStatus() != JobStatus.OPEN) {
            throw new IllegalStateException("Cannot apply to a job that is not open");
        }

        Candidate candidate = candidateRepository.findByEmail(candidateEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found"));

        if (applicationRepository.existsByJobIdAndCandidateId(jobId, candidate.getId())) {
            throw new IllegalStateException("You have already applied to this job");
        }

        Application application = new Application();
        application.setJob(job);
        application.setCandidate(candidate);
        application.setStatus(ApplicationStatus.APPLIED);
        application.setCoverNote(coverNote);

        if (resume != null && !resume.isEmpty()) {
            application.setResumePath(storeResume(resume, candidate.getId(), jobId));
        }

        return applicationMapper.toResponseDTO(applicationRepository.save(application));
    }

    @Override
    public ApplicationResponseDTO withdrawApplication(Long id, String candidateEmail) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        if (!application.getCandidate().getEmail().equals(candidateEmail)) {
            throw new AccessDeniedException("You are not authorized to withdraw this application");
        }

        applicationPipeline.validateWithdrawal(application.getStatus());

        application.setStatus(ApplicationStatus.WITHDRAWN);
        return applicationMapper.toResponseDTO(applicationRepository.save(application));
    }

    @Override
    public ApplicationRecruiterViewDTO changeStatus(Long id, ApplicationStatus status) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));

        applicationPipeline.validateRecruiterTransition(application.getStatus(), status);

        application.setStatus(status);
        return applicationMapper.toRecruiterViewDTO(applicationRepository.save(application));
    }

    @Override
    public ApplicationRecruiterViewDTO rateApplication(Long id, Integer rating) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        application.setRating(rating);
        return applicationMapper.toRecruiterViewDTO(applicationRepository.save(application));
    }

    @Override
    public ApplicationNoteResponseDTO addNote(Long applicationId, String content, String recruiterEmail) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + applicationId));

        Recruiter author = recruiterRepository.findByEmail(recruiterEmail)
                .orElseThrow(() -> new ResourceNotFoundException("Recruiter not found"));

        ApplicationNote note = new ApplicationNote();
        note.setApplication(application);
        note.setAuthor(author);
        note.setContent(content);

        return applicationMapper.toNoteResponseDTO(applicationNoteRepository.save(note));
    }

    @Override
    public List<ApplicationNoteResponseDTO> getNotes(Long applicationId) {
        if (!applicationRepository.existsById(applicationId)) {
            throw new ResourceNotFoundException("Application not found with id: " + applicationId);
        }
        return applicationNoteRepository.findByApplicationIdOrderByCreatedAtDesc(applicationId).stream()
                .map(applicationMapper::toNoteResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public String getResumePath(Long id) {
        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found with id: " + id));
        return application.getResumePath();
    }

    private String storeResume(MultipartFile file, Long candidateId, Long jobId) {
        validateResumeFile(file);

        String uploadDir = "uploads/resumes/";
        String safeOriginalName = Paths.get(file.getOriginalFilename() != null
                ? file.getOriginalFilename() : "resume.pdf").getFileName().toString();
        String filename = candidateId + "_" + jobId + "_" + System.currentTimeMillis() + "_" + safeOriginalName;

        try {
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }
            Path filePath = uploadPath.resolve(filename);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return filePath.toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to store resume file", e);
        }
    }

    private void validateResumeFile(MultipartFile file) {
        long maxSize = 5 * 1024 * 1024;
        if (file.getSize() > maxSize) {
            throw new IllegalArgumentException("Resume file must be under 5MB");
        }

        String contentType = file.getContentType();
        String filename = file.getOriginalFilename();
        boolean looksLikePdfByName = filename != null && filename.toLowerCase().endsWith(".pdf");
        boolean acceptableContentType = "application/pdf".equals(contentType)
                || "application/octet-stream".equals(contentType);

        if (!acceptableContentType || !looksLikePdfByName) {
            throw new IllegalArgumentException("Resume must be a PDF file");
        }
    }
}
