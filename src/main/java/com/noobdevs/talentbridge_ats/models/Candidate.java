package com.noobdevs.talentbridge_ats.models;

import jakarta.persistence.CascadeType;
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
public class Candidate extends User{

    @OneToMany(mappedBy = "candidate", cascade = CascadeType.REMOVE)
    private List<Application> applications;

    public Candidate(String name, String email, String password, List<Application> applications) {
        super(name, email, password);
        this.applications = applications;
    }
}
