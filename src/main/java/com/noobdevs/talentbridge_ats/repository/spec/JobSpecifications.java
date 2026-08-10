package com.noobdevs.talentbridge_ats.repository.spec;

import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.models.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecifications {

    public static Specification<Job> hasStatus(JobStatus status) {
        return (root, query, cb) -> status == null ? null : cb.equal(root.get("status"), status);
    }

    public static Specification<Job> hasWorkMode(String workMode) {
        return (root, query, cb) -> (workMode == null || workMode.isBlank()) ? null
                : cb.equal(cb.lower(root.get("work_mode")), workMode.toLowerCase());
    }

    public static Specification<Job> hasEmploymentType(String employmentType) {
        return (root, query, cb) -> (employmentType == null || employmentType.isBlank()) ? null
                : cb.equal(cb.lower(root.get("employment_type")), employmentType.toLowerCase());
    }

    public static Specification<Job> hasLocation(String location) {
        return (root, query, cb) -> (location == null || location.isBlank()) ? null
                : cb.like(cb.lower(root.get("location")), "%" + location.toLowerCase() + "%");
    }

    public static Specification<Job> titleContains(String keyword) {
        return (root, query, cb) -> (keyword == null || keyword.isBlank()) ? null
                : cb.like(cb.lower(root.get("title")), "%" + keyword.toLowerCase() + "%");
    }
}
