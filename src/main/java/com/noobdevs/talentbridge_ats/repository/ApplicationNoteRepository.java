package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.models.ApplicationNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationNoteRepository extends JpaRepository<ApplicationNote, Long> {
    List<ApplicationNote> findByApplicationIdOrderByCreatedAtDesc(Long applicationId);
}
