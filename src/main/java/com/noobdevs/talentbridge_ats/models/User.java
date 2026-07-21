package com.noobdevs.talentbridge_ats.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "user_table")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String Email;
    private String password;
    private String role;

}
