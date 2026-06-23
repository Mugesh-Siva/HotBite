package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.UserAddressRequestDTO;
import com.hexaware.hotbyte.dto.UserAddressResponseDTO;
import com.hexaware.hotbyte.service.UserAddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/user-addresses")
public class UserAddressController {

    @Autowired
    UserAddressService userAddressService;

    @PostMapping("/add")
    public ResponseEntity<UserAddressResponseDTO> createUserAddress(@Valid @RequestBody UserAddressRequestDTO dto) {
        log.info("createUserAddress endpoint called");
        UserAddressResponseDTO response = userAddressService.createUserAddress(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UserAddressResponseDTO> updateUserAddress(@PathVariable Integer id, @Valid @RequestBody UserAddressRequestDTO dto) {
        log.info("updateUserAddress endpoint called with id: {}", id);
        UserAddressResponseDTO response = userAddressService.updateUserAddress(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<UserAddressResponseDTO> getUserAddressById(@PathVariable Integer id) {
        log.info("getUserAddressById endpoint called with id: {}", id);
        UserAddressResponseDTO response = userAddressService.getUserAddressById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<UserAddressResponseDTO>> getAllUserAddresses() {
        log.info("getAllUserAddresses endpoint called");
        List<UserAddressResponseDTO> response = userAddressService.getAllUserAddresses();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteUserAddressById(@PathVariable Integer id) {
        log.info("deleteUserAddressById endpoint called with id: {}", id);
        userAddressService.deleteUserAddress(id);
        return new ResponseEntity<>("User Address deleted successfully", HttpStatus.ACCEPTED);
    }
}
