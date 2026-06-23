package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Integer orderId;
    private Integer userId;
    private Integer restaurantId;
    private Integer shippingAddressId;
    private Double totalAmount;
    private String orderStatus;
    private String paymentMethod;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime createdAt;
}
