package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends JpaRepository<Job,Long> {
}
