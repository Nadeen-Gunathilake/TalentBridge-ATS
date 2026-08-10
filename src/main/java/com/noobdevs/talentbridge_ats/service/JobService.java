package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.enums.JobStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

   Page<JobResponseDTO> getAllJobs(boolean isRecruiter, JobStatus status, String workMode,
                                   String employmentType, String location, String keyword, Pageable pageable);

   JobResponseDTO getJobById(Long id, boolean isRecruiter);
   JobResponseDTO createJob(JobRequestDTO dto, String recruiterEmail);
   JobResponseDTO updateJob(Long id, JobRequestDTO dto);
   JobResponseDTO changeStatus(Long id, JobStatus status);
   void deleteJob(Long id);
}
