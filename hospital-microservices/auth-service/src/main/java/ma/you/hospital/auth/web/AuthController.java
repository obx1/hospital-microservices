package ma.you.hospital.auth.web;

import jakarta.validation.Valid;
import ma.you.hospital.auth.dto.AuthResponse;
import ma.you.hospital.auth.dto.LoginRequest;
import ma.you.hospital.auth.dto.RegisterRequest;
import ma.you.hospital.auth.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}
