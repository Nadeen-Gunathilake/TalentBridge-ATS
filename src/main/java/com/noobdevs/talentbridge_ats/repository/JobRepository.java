package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepository extends JpaRepository<Job, Long>, JpaSpecificationExecutor<Job> {
    List<Job> findByStatus(JobStatus status);
    List<Job> findByCreatedById(Long recruiterId);
}
