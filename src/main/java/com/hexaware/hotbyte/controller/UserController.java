package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.UserRequestDTO;
import com.hexaware.hotbyte.dto.UserResponseDTO;
import com.hexaware.hotbyte.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("/add")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO dto) {
        log.info("createUser endpoint called");
        UserResponseDTO response = userService.createUser(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Integer id, @Valid @RequestBody UserRequestDTO dto) {
        log.info("updateUser endpoint called with id: {}", id);
        UserResponseDTO response = userService.updateUser(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Integer id) {
        log.info("getUserById endpoint called with id: {}", id);
        UserResponseDTO response = userService.getUserById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        log.info("getAllUsers endpoint called");
        List<UserResponseDTO> response = userService.getAllUsers();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Integer id) {
        log.info("deleteUserById endpoint called with id: {}", id);
        userService.deleteUser(id);
        return new ResponseEntity<>("User deleted successfully", HttpStatus.ACCEPTED);
    }
}
