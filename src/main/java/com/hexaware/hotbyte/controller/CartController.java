package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.CartRequestDTO;
import com.hexaware.hotbyte.dto.CartResponseDTO;
import com.hexaware.hotbyte.service.CartService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/carts")
public class CartController {

    @Autowired
    CartService cartService;

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> createCart(@Valid @RequestBody CartRequestDTO dto) {
        log.info("createCart endpoint called");
        CartResponseDTO response = cartService.createCart(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartResponseDTO> updateCart(@PathVariable Integer id, @Valid @RequestBody CartRequestDTO dto) {
        log.info("updateCart endpoint called with id: {}", id);
        CartResponseDTO response = cartService.updateCart(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<CartResponseDTO> getCartById(@PathVariable Integer id) {
        log.info("getCartById endpoint called with id: {}", id);
        CartResponseDTO response = cartService.getCartById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CartResponseDTO>> getAllCarts() {
        log.info("getAllCarts endpoint called");
        List<CartResponseDTO> response = cartService.getAllCarts();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteCartById(@PathVariable Integer id) {
        log.info("deleteCartById endpoint called with id: {}", id);
        cartService.deleteCart(id);
        return new ResponseEntity<>("Cart deleted successfully", HttpStatus.ACCEPTED);
    }
}
