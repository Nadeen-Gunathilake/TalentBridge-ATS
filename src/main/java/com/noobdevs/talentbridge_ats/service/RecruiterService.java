package com.noobdevs.talentbridge_ats.service;

import com.noobdevs.talentbridge_ats.dto.RecruiterRequestDTO;
import com.noobdevs.talentbridge_ats.dto.RecruiterResponseDTO;

public interface RecruiterService {

    RecruiterResponseDTO getRecruiterById(Long id);
    RecruiterResponseDTO createRecruiter(RecruiterRequestDTO dto);
    RecruiterResponseDTO updateRecruiter(Long id,RecruiterRequestDTO dto);
    void deleteRecruiter(Long id);
}
