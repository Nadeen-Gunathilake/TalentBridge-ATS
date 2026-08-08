package com.noobdevs.talentbridge_ats.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Recruiter extends User{

    @Column(nullable = false)
    private String recruiterType;

    @OneToMany(mappedBy = "createdBy")
    private List<Job> jobs;

    public Recruiter(String name, String email, String password, String recruiterType) {
        super(name,email,password);
        this.recruiterType = recruiterType;
    }
}
