package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.PaymentRequestDTO;
import com.hexaware.hotbyte.dto.PaymentResponseDTO;
import com.hexaware.hotbyte.service.PaymentService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @Autowired
    PaymentService paymentService;

    @PostMapping("/add")
    public ResponseEntity<PaymentResponseDTO> createPayment(@Valid @RequestBody PaymentRequestDTO dto) {
        log.info("createPayment endpoint called");
        PaymentResponseDTO response = paymentService.createPayment(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<PaymentResponseDTO> updatePayment(@PathVariable Integer id, @Valid @RequestBody PaymentRequestDTO dto) {
        log.info("updatePayment endpoint called with id: {}", id);
        PaymentResponseDTO response = paymentService.updatePayment(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<PaymentResponseDTO> getPaymentById(@PathVariable Integer id) {
        log.info("getPaymentById endpoint called with id: {}", id);
        PaymentResponseDTO response = paymentService.getPaymentById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<PaymentResponseDTO>> getAllPayments() {
        log.info("getAllPayments endpoint called");
        List<PaymentResponseDTO> response = paymentService.getAllPayments();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deletePaymentById(@PathVariable Integer id) {
        log.info("deletePaymentById endpoint called with id: {}", id);
        paymentService.deletePayment(id);
        return new ResponseEntity<>("Payment deleted successfully", HttpStatus.ACCEPTED);
    }
}
