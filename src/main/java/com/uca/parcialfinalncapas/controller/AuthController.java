package com.uca.parcialfinalncapas.controller;

import com.uca.parcialfinalncapas.dto.request.AuthRequest;
import com.uca.parcialfinalncapas.dto.response.AuthResponse;
import com.uca.parcialfinalncapas.repository.UserRepository;
import com.uca.parcialfinalncapas.utils.JwtUtil;
import com.uca.parcialfinalncapas.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getCorreo(), authRequest.getPassword()
                    )
            );

            User user = userRepository.findByCorreo(authRequest.getCorreo())
                    .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            String token = jwtUtil.generateToken(user);
            return ResponseEntity.ok(new AuthResponse(token));
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
        }
    }
}
