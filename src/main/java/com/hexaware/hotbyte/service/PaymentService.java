package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.PaymentRequestDTO;
import com.hexaware.hotbyte.dto.PaymentResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface PaymentService {
    PaymentResponseDTO createPayment(PaymentRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    PaymentResponseDTO updatePayment(Integer id, PaymentRequestDTO dto) throws PaymentNotFoundException, InvalidInputException;
    void deletePayment(Integer id) throws PaymentNotFoundException;
    PaymentResponseDTO getPaymentById(Integer id) throws PaymentNotFoundException;
    List<PaymentResponseDTO> getAllPayments();
}
