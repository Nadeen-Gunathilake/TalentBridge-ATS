package com.noobdevs.talentbridge_ats.config;
import com.noobdevs.talentbridge_ats.models.Recruiter;
import com.noobdevs.talentbridge_ats.repository.RecruiterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminSeeder implements ApplicationRunner {

    private final RecruiterRepository recruiterRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.name}")
    private String name;

    @Value("${app.admin.email}")
    private String email;

    @Value("${app.admin.password}")
    private String password;

    @Value("${app.admin.department}")
    private String recruiterType;

    @Override
    public void run(ApplicationArguments args) {
        if (recruiterRepository.count() == 0) {
            Recruiter recruiter = new Recruiter(name, email, passwordEncoder.encode(password), recruiterType);
            recruiterRepository.save(recruiter);
        }
    }
}
