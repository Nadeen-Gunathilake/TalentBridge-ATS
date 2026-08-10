package com.noobdevs.talentbridge_ats.controller;

import com.noobdevs.talentbridge_ats.dto.AuthRequest;
import com.noobdevs.talentbridge_ats.dto.AuthResponse;
import com.noobdevs.talentbridge_ats.dto.CandidateRequestDTO;
import com.noobdevs.talentbridge_ats.dto.CandidateResponseDTO;
import com.noobdevs.talentbridge_ats.dto.*;
import com.noobdevs.talentbridge_ats.security.JwtUtil;
import com.noobdevs.talentbridge_ats.service.CandidateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final CandidateService candidateService;

    @PostMapping("/register")
    public ResponseEntity<CandidateResponseDTO> register(@Valid @RequestBody CandidateRequestDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateService.createCandidate(dto));
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request){
        try {
            var authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())

            );
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(GrantedAuthority::getAuthority)
                    .map(a -> a.replace("ROLE_", ""))
                    .orElseThrow();

            String token = jwtUtil.generateToken(request.getEmail(), role);
            return new AuthResponse(token, role);
        }catch (BadCredentialsException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Invalid email or password");

        }

    }
}
