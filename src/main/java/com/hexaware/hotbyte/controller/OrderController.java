package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.OrderRequestDTO;
import com.hexaware.hotbyte.dto.OrderResponseDTO;
import com.hexaware.hotbyte.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    OrderService orderService;

    @PostMapping("/add")
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO dto) {
        log.info("createOrder endpoint called");
        OrderResponseDTO response = orderService.createOrder(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderResponseDTO> updateOrder(@PathVariable Integer id, @Valid @RequestBody OrderRequestDTO dto) {
        log.info("updateOrder endpoint called with id: {}", id);
        OrderResponseDTO response = orderService.updateOrder(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<OrderResponseDTO> getOrderById(@PathVariable Integer id) {
        log.info("getOrderById endpoint called with id: {}", id);
        OrderResponseDTO response = orderService.getOrderById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<OrderResponseDTO>> getAllOrders() {
        log.info("getAllOrders endpoint called");
        List<OrderResponseDTO> response = orderService.getAllOrders();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteOrderById(@PathVariable Integer id) {
        log.info("deleteOrderById endpoint called with id: {}", id);
        orderService.deleteOrder(id);
        return new ResponseEntity<>("Order deleted successfully", HttpStatus.ACCEPTED);
    }
}
