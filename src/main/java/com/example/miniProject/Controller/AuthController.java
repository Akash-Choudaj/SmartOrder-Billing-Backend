package com.example.miniProject.Controller;

import com.example.miniProject.Security.JwtUtil;
import com.example.miniProject.dto.LoginRequest;
import com.example.miniProject.dto.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserDetailsService userDetailsService;

    // 🔹 User Login
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return authenticateAndGenerateToken(request, "ROLE_USER");
    }

    // 🔹 Admin Login
    @PostMapping("/admin/login")
    public ResponseEntity<LoginResponse> adminLogin(@RequestBody LoginRequest request) {
        return authenticateAndGenerateToken(request, "ROLE_ADMIN");
    }

    private ResponseEntity<LoginResponse> authenticateAndGenerateToken(LoginRequest request, String requiredRole) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String roles = String.join(",", userDetails.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .toList());

        // ✅ Validate role for correct endpoint
        if (!roles.contains(requiredRole)) {
            throw new RuntimeException("Access denied! This account is not authorized for " + requiredRole + " login.");
        }

        final String jwt = jwtUtil.generateToken(userDetails.getUsername(), roles);

        LoginResponse response = new LoginResponse();
        response.setJwt(jwt);
        return ResponseEntity.ok(response);
    }
}
