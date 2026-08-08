package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.models.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CandidateRepository extends JpaRepository<Candidate,Long> {
    Optional<Candidate> findByEmail(String email);
    boolean existsByEmail(String email);
}
