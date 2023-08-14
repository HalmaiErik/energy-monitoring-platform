package com.distributedsystems.energyplatform.controller;

import com.distributedsystems.energyplatform.dto.LoginRequestDto;
import com.distributedsystems.energyplatform.dto.LoginResponseDto;
import com.distributedsystems.energyplatform.dto.UserDto;
import com.distributedsystems.energyplatform.dto.UserEditDto;
import com.distributedsystems.energyplatform.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/api/new/user")
    public ResponseEntity<Long> insertUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.register(userDto));
    }

    @PostMapping("/api/edit/user")
    public ResponseEntity<Long> editUser(@RequestBody UserEditDto userEditDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(userService.editUser(userEditDto));
    }

    @DeleteMapping("/api/delete/user/{id}")
    public ResponseEntity<Long> deleteUser(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.deleteUser(id));
    }

    @GetMapping("/api/view/users")
    public ResponseEntity<List<UserDto>> findAllClients() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.findAllClients());
    }

    @PostMapping("/api/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto) {
        return userService.login(loginRequestDto);
    }
}
