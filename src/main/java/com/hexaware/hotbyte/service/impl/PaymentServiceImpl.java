package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.PaymentRequestDTO;
import com.hexaware.hotbyte.dto.PaymentResponseDTO;
import com.hexaware.hotbyte.entity.Order;
import com.hexaware.hotbyte.entity.Payment;
import com.hexaware.hotbyte.repository.OrderRepository;
import com.hexaware.hotbyte.repository.PaymentRepository;
import com.hexaware.hotbyte.service.PaymentService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public PaymentResponseDTO createPayment(PaymentRequestDTO dto) {
        log.info("createPayment called with dto: {}", dto);
        Payment payment = new Payment();
        if (dto.getAmount() != null) payment.setAmount(BigDecimal.valueOf(dto.getAmount()));
        payment.setTransactionId(dto.getTransactionId());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + dto.getOrderId()));
        payment.setOrder(order);

        Payment savedPayment = paymentRepository.save(payment);
        return mapToDTO(savedPayment);
    }

    @Override
    public PaymentResponseDTO updatePayment(Integer id, PaymentRequestDTO dto) {
        log.info("updatePayment called with id: {}, dto: {}", id, dto);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));

        if (dto.getAmount() != null) payment.setAmount(BigDecimal.valueOf(dto.getAmount()));
        payment.setTransactionId(dto.getTransactionId());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + dto.getOrderId()));
        payment.setOrder(order);

        Payment updatedPayment = paymentRepository.save(payment);
        return mapToDTO(updatedPayment);
    }

    @Override
    public void deletePayment(Integer id) {
        log.info("deletePayment called with id: {}", id);
        if (!paymentRepository.existsById(id)) {
            throw new PaymentNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }

    @Override
    public PaymentResponseDTO getPaymentById(Integer id) {
        log.info("getPaymentById called with id: {}", id);
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found with ID: " + id));
        return mapToDTO(payment);
    }

    @Override
    public List<PaymentResponseDTO> getAllPayments() {
        log.info("getAllPayments called");
        return paymentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private PaymentResponseDTO mapToDTO(Payment payment) {
        PaymentResponseDTO dto = new PaymentResponseDTO();
        dto.setPaymentId(payment.getPaymentId());
        if (payment.getAmount() != null) dto.setAmount(payment.getAmount().doubleValue());
        dto.setTransactionId(payment.getTransactionId());
        if(payment.getOrder() != null) dto.setOrderId(payment.getOrder().getOrderId());
        return dto;
    }
}
