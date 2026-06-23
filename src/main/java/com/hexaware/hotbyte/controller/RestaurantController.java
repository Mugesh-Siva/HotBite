package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.RestaurantRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantResponseDTO;
import com.hexaware.hotbyte.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {

    @Autowired
    RestaurantService restaurantService;

    @PostMapping("/add")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO dto) {
        log.info("createRestaurant endpoint called");
        RestaurantResponseDTO response = restaurantService.createRestaurant(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RestaurantResponseDTO> updateRestaurant(@PathVariable Integer id, @Valid @RequestBody RestaurantRequestDTO dto) {
        log.info("updateRestaurant endpoint called with id: {}", id);
        RestaurantResponseDTO response = restaurantService.updateRestaurant(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@PathVariable Integer id) {
        log.info("getRestaurantById endpoint called with id: {}", id);
        RestaurantResponseDTO response = restaurantService.getRestaurantById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<RestaurantResponseDTO>> getAllRestaurants() {
        log.info("getAllRestaurants endpoint called");
        List<RestaurantResponseDTO> response = restaurantService.getAllRestaurants();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteRestaurantById(@PathVariable Integer id) {
        log.info("deleteRestaurantById endpoint called with id: {}", id);
        restaurantService.deleteRestaurant(id);
        return new ResponseEntity<>("Restaurant deleted successfully", HttpStatus.ACCEPTED);
    }
}
