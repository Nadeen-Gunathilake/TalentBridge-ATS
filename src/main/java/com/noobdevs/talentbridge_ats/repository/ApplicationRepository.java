package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import com.noobdevs.talentbridge_ats.models.Application;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByJobId(Long jobId);
    boolean existsByJobIdAndCandidateId(Long jobId, Long candidateId);

    Page<Application> findByJobId(Long jobId, Pageable pageable);
    Page<Application> findByJobIdAndStatus(Long jobId, ApplicationStatus status, Pageable pageable);
}
