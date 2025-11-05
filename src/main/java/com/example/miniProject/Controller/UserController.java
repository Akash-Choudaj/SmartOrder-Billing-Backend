package com.example.miniProject.Controller;

import com.example.miniProject.Entity.User;
import com.example.miniProject.Service.UserService;
import com.example.miniProject.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    public UserService userServ;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = userServ.getAllUsers().stream().map(this::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id:[0-9]+}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userServ.getUserById(id).orElseThrow(() -> new RuntimeException("User Not Found"));
        return ResponseEntity.ok(toDto(user));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
        User user = toEntity(userDto);
        if (userDto.getRoles() == null || userDto.getRoles().isEmpty()) {
            user.getRoles().add("USER");
        } else {
            user.setRoles(userDto.getRoles());
        }
        user.setPassword(userDto.getPassword());
        User created = userServ.saveUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(created));
    }

    private UserDto toDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setRoles(user.getRoles());
        // Do NOT return password in DTO!
        return dto;
    }

    private User toEntity(UserDto dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setRoles(dto.getRoles() == null ? new java.util.HashSet<>() : dto.getRoles());
        return user;
    }
}
