package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.OrderItemRequestDTO;
import com.hexaware.hotbyte.dto.OrderItemResponseDTO;
import com.hexaware.hotbyte.service.OrderItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    @Autowired
    OrderItemService orderItemService;

    @PostMapping("/add")
    public ResponseEntity<OrderItemResponseDTO> createOrderItem(@Valid @RequestBody OrderItemRequestDTO dto) {
        log.info("createOrderItem endpoint called");
        OrderItemResponseDTO response = orderItemService.createOrderItem(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<OrderItemResponseDTO> updateOrderItem(@PathVariable Integer id, @Valid @RequestBody OrderItemRequestDTO dto) {
        log.info("updateOrderItem endpoint called with id: {}", id);
        OrderItemResponseDTO response = orderItemService.updateOrderItem(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<OrderItemResponseDTO> getOrderItemById(@PathVariable Integer id) {
        log.info("getOrderItemById endpoint called with id: {}", id);
        OrderItemResponseDTO response = orderItemService.getOrderItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<OrderItemResponseDTO>> getAllOrderItems() {
        log.info("getAllOrderItems endpoint called");
        List<OrderItemResponseDTO> response = orderItemService.getAllOrderItems();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteOrderItemById(@PathVariable Integer id) {
        log.info("deleteOrderItemById endpoint called with id: {}", id);
        orderItemService.deleteOrderItem(id);
        return new ResponseEntity<>("Order Item deleted successfully", HttpStatus.ACCEPTED);
    }
}
