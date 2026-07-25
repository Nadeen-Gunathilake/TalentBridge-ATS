package com.noobdevs.talentbridge_ats.repository;

import com.noobdevs.talentbridge_ats.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
