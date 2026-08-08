package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.enums.JobStatus;

import java.util.List;

public interface JobService {
   List<JobResponseDTO> getAllJobs(boolean isRecruiter);
   JobResponseDTO getJobById(Long id, boolean isRecruiter);
   JobResponseDTO createJob(JobRequestDTO dto, String recruiterEmail);
   JobResponseDTO updateJob(Long id, JobRequestDTO dto);
   JobResponseDTO changeStatus(Long id, JobStatus status);
   void deleteJob(Long id);

}
