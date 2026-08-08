package com.noobdevs.talentbridge_ats.service.impl;
import com.noobdevs.talentbridge_ats.dto.CandidateRequestDTO;
import com.noobdevs.talentbridge_ats.dto.CandidateResponseDTO;
import com.noobdevs.talentbridge_ats.exception.ResourceNotFoundException;
import com.noobdevs.talentbridge_ats.mapper.CandidateMapper;
import com.noobdevs.talentbridge_ats.models.Candidate;
import com.noobdevs.talentbridge_ats.repository.CandidateRepository;
import com.noobdevs.talentbridge_ats.service.CandidateService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateMapper candidateMapper;
    private final CandidateRepository candidateRepository;
    private final PasswordEncoder passwordEncoder;

    public CandidateServiceImpl(CandidateMapper candidateMapper, CandidateRepository candidateRepository, PasswordEncoder passwordEncoder) {
        this.candidateMapper = candidateMapper;
        this.candidateRepository = candidateRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public CandidateResponseDTO getCandidateById(Long id,String authenticatedEmail){
        Candidate existing = candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        if (!existing.getEmail().equals(authenticatedEmail)) {
            throw new AccessDeniedException("You are not authorized to update this candidate");
        }

        return candidateMapper.toResponseDTO(existing);

    }

    @Override
    public CandidateResponseDTO createCandidate(CandidateRequestDTO dto){
        Candidate candidate=candidateMapper.toEntity(dto);
        candidate.setPassword(passwordEncoder.encode(dto.getPassword()));
        return candidateMapper.toResponseDTO(candidateRepository.save(candidate));
    }

    @Override
    public CandidateResponseDTO updateCandidate(Long id,CandidateRequestDTO dto,String authenticatedEmail){
        Candidate existing=candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        if (!existing.getEmail().equals(authenticatedEmail)) {
            throw new AccessDeniedException("You are not authorized to update this candidate");
        }

        existing.setName(dto.getName());
        existing.setEmail(dto.getEmail());
        existing.setPassword(passwordEncoder.encode(dto.getPassword()));

        return candidateMapper.toResponseDTO(candidateRepository.save(existing));
    }

    @Override
    public void deleteCandidate(Long id,String authenticatedEmail){
        Candidate existing=candidateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Candidate not found with id: " + id));

        if (!existing.getEmail().equals(authenticatedEmail)) {
            throw new AccessDeniedException("You are not authorized to delete this candidate");
        }

        candidateRepository.deleteById(id);
    }


}
