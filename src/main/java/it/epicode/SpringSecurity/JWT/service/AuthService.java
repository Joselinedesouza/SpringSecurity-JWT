package it.epicode.SpringSecurity.JWT.service;


import it.epicode.SpringSecurity.JWT.dto.AuthResponse;
import it.epicode.SpringSecurity.JWT.dto.LoginRequest;
import it.epicode.SpringSecurity.JWT.dto.RegisterRequest;
import it.epicode.SpringSecurity.JWT.entity.Role;
import it.epicode.SpringSecurity.JWT.entity.User;
import it.epicode.SpringSecurity.JWT.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;

    public AuthResponse register(RegisterRequest request) {
        if (userRepo.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email già registrata");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nome(request.getNome())
                .role(request.getRole() != null ? request.getRole() : Role.UTENTE)
                .build();

        userRepo.save(user);
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getEmail(), request.getPassword()));

        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
