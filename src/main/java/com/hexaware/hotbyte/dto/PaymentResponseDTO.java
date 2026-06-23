package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDTO {
    private Integer paymentId;
    private Integer orderId;
    private String transactionId;
    private String paymentStatus;
    private Double amount;
    private LocalDateTime processedAt;
}
