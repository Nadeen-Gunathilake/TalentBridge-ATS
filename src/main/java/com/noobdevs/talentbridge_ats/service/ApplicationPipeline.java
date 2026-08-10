package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.enums.ApplicationStatus;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

@Component
public class ApplicationPipeline {

    private static final Map<ApplicationStatus, Set<ApplicationStatus>> RECRUITER_TRANSITIONS = new EnumMap<>(ApplicationStatus.class);

    static {
        RECRUITER_TRANSITIONS.put(ApplicationStatus.APPLIED, EnumSet.of(ApplicationStatus.UNDER_REVIEW, ApplicationStatus.REJECTED));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.UNDER_REVIEW, EnumSet.of(ApplicationStatus.SHORTLISTED, ApplicationStatus.REJECTED));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.SHORTLISTED, EnumSet.of(ApplicationStatus.INTERVIEW, ApplicationStatus.REJECTED));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.INTERVIEW, EnumSet.of(ApplicationStatus.OFFER, ApplicationStatus.REJECTED));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.OFFER, EnumSet.of(ApplicationStatus.HIRED, ApplicationStatus.REJECTED));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.HIRED, EnumSet.noneOf(ApplicationStatus.class));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.REJECTED, EnumSet.noneOf(ApplicationStatus.class));
        RECRUITER_TRANSITIONS.put(ApplicationStatus.WITHDRAWN, EnumSet.noneOf(ApplicationStatus.class));
    }

    private static final Set<ApplicationStatus> WITHDRAWABLE_FROM = EnumSet.of(
            ApplicationStatus.APPLIED,
            ApplicationStatus.UNDER_REVIEW,
            ApplicationStatus.SHORTLISTED,
            ApplicationStatus.INTERVIEW,
            ApplicationStatus.OFFER
    );

    public void validateRecruiterTransition(ApplicationStatus from, ApplicationStatus to) {
        if (to == ApplicationStatus.WITHDRAWN) {
            throw new IllegalStateException("Only the candidate can withdraw their own application");
        }
        Set<ApplicationStatus> allowed = RECRUITER_TRANSITIONS.getOrDefault(from, EnumSet.noneOf(ApplicationStatus.class));
        if (!allowed.contains(to)) {
            throw new IllegalStateException("Cannot move an application from " + from + " to " + to);
        }
    }

    public void validateWithdrawal(ApplicationStatus current) {
        if (!WITHDRAWABLE_FROM.contains(current)) {
            throw new IllegalStateException("Cannot withdraw an application that is already " + current);
        }
    }
}
