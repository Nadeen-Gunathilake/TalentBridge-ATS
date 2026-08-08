package com.noobdevs.talentbridge_ats.service;
import com.noobdevs.talentbridge_ats.dto.CandidateRequestDTO;
import com.noobdevs.talentbridge_ats.dto.CandidateResponseDTO;

public interface CandidateService {

    CandidateResponseDTO getCandidateById(Long id,String authenticatedEmail);
    CandidateResponseDTO createCandidate(CandidateRequestDTO dto);
    CandidateResponseDTO updateCandidate(Long id,CandidateRequestDTO dto,String authenticatedEmail);
    void deleteCandidate(Long id,String authenticatedEmail);

}
