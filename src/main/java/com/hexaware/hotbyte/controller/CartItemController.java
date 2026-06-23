package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.CartItemRequestDTO;
import com.hexaware.hotbyte.dto.CartItemResponseDTO;
import com.hexaware.hotbyte.service.CartItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart-items")
public class CartItemController {

    @Autowired
    CartItemService cartItemService;

    @PostMapping("/add")
    public ResponseEntity<CartItemResponseDTO> createCartItem(@Valid @RequestBody CartItemRequestDTO dto) {
        log.info("createCartItem endpoint called");
        CartItemResponseDTO response = cartItemService.createCartItem(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CartItemResponseDTO> updateCartItem(@PathVariable Integer id, @Valid @RequestBody CartItemRequestDTO dto) {
        log.info("updateCartItem endpoint called with id: {}", id);
        CartItemResponseDTO response = cartItemService.updateCartItem(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<CartItemResponseDTO> getCartItemById(@PathVariable Integer id) {
        log.info("getCartItemById endpoint called with id: {}", id);
        CartItemResponseDTO response = cartItemService.getCartItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CartItemResponseDTO>> getAllCartItems() {
        log.info("getAllCartItems endpoint called");
        List<CartItemResponseDTO> response = cartItemService.getAllCartItems();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteCartItemById(@PathVariable Integer id) {
        log.info("deleteCartItemById endpoint called with id: {}", id);
        cartItemService.deleteCartItem(id);
        return new ResponseEntity<>("Cart Item deleted successfully", HttpStatus.ACCEPTED);
    }
}
