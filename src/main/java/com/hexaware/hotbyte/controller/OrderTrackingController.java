package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.OrderTrackingRequestDTO;
import com.hexaware.hotbyte.dto.OrderTrackingResponseDTO;
import com.hexaware.hotbyte.service.OrderTrackingService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order-trackings")
public class OrderTrackingController {

    @Autowired
    OrderTrackingService orderTrackingService;

    @PostMapping("/add")
    public ResponseEntity<OrderTrackingResponseDTO> createOrderTracking(@Valid @RequestBody OrderTrackingRequestDTO dto) {
        log.info("createOrderTracking endpoint called");
        OrderTrackingResponseDTO response = orderTrackingService.createOrderTracking(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderTrackingResponseDTO> updateOrderTracking(@PathVariable Integer id, @Valid @RequestBody OrderTrackingRequestDTO dto) {
        log.info("updateOrderTracking endpoint called with id: {}", id);
        OrderTrackingResponseDTO response = orderTrackingService.updateOrderTracking(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<OrderTrackingResponseDTO> getOrderTrackingById(@PathVariable Integer id) {
        log.info("getOrderTrackingById endpoint called with id: {}", id);
        OrderTrackingResponseDTO response = orderTrackingService.getOrderTrackingById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<OrderTrackingResponseDTO>> getAllOrderTrackings() {
        log.info("getAllOrderTrackings endpoint called");
        List<OrderTrackingResponseDTO> response = orderTrackingService.getAllOrderTrackings();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteOrderTrackingById(@PathVariable Integer id) {
        log.info("deleteOrderTrackingById endpoint called with id: {}", id);
        orderTrackingService.deleteOrderTracking(id);
        return new ResponseEntity<>("Order Tracking deleted successfully", HttpStatus.ACCEPTED);
    }
}
