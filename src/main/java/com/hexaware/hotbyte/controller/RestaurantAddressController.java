package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.RestaurantAddressRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantAddressResponseDTO;
import com.hexaware.hotbyte.service.RestaurantAddressService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/restaurant-addresses")
public class RestaurantAddressController {

    @Autowired
    RestaurantAddressService restaurantAddressService;

    @PostMapping("/add")
    public ResponseEntity<RestaurantAddressResponseDTO> createRestaurantAddress(@Valid @RequestBody RestaurantAddressRequestDTO dto) {
        log.info("createRestaurantAddress endpoint called");
        RestaurantAddressResponseDTO response = restaurantAddressService.createRestaurantAddress(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RestaurantAddressResponseDTO> updateRestaurantAddress(@PathVariable Integer id, @Valid @RequestBody RestaurantAddressRequestDTO dto) {
        log.info("updateRestaurantAddress endpoint called with id: {}", id);
        RestaurantAddressResponseDTO response = restaurantAddressService.updateRestaurantAddress(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<RestaurantAddressResponseDTO> getRestaurantAddressById(@PathVariable Integer id) {
        log.info("getRestaurantAddressById endpoint called with id: {}", id);
        RestaurantAddressResponseDTO response = restaurantAddressService.getRestaurantAddressById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<RestaurantAddressResponseDTO>> getAllRestaurantAddresses() {
        log.info("getAllRestaurantAddresses endpoint called");
        List<RestaurantAddressResponseDTO> response = restaurantAddressService.getAllRestaurantAddresses();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteRestaurantAddressById(@PathVariable Integer id) {
        log.info("deleteRestaurantAddressById endpoint called with id: {}", id);
        restaurantAddressService.deleteRestaurantAddress(id);
        return new ResponseEntity<>("Restaurant Address deleted successfully", HttpStatus.ACCEPTED);
    }
}
