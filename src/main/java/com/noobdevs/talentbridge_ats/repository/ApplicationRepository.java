package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.models.Application;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {
}
