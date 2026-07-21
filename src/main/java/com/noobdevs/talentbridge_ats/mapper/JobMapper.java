package com.noobdevs.talentbridge_ats.mapper;

import com.noobdevs.talentbridge_ats.dto.JobRequestDTO;
import com.noobdevs.talentbridge_ats.dto.JobResponseDTO;
import com.noobdevs.talentbridge_ats.enums.JobStatus;
import com.noobdevs.talentbridge_ats.models.Job;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class JobMapper {

    public JobResponseDTO toResponseDTO(Job entity) {
        JobResponseDTO dto = new JobResponseDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setLocation(entity.getLocation());
        dto.setWork_mode(entity.getWork_mode());
        dto.setEmployment_type(entity.getEmployment_type());
        dto.setSalary_range(entity.getSalary_range());
        dto.setRequired_skills(entity.getRequired_skills());
        dto.setClosing_date(entity.getClosing_date());
        dto.setStatus(entity.getStatus());
        return dto;

    }

    public Job toEntity(JobRequestDTO dto) {
        Job entity = new Job();

        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLocation(dto.getLocation());
        entity.setWork_mode(dto.getWork_mode());
        entity.setEmployment_type(dto.getEmployment_type());
        entity.setRequired_skills(dto.getRequired_skills());
        entity.setSalary_range(dto.getSalary_range());
        entity.setClosing_date(dto.getClosing_date());
        entity.setStatus(dto.getStatus());

        return entity;
    }

}
