package com.hexaware.hotbyte.dto;

import jakarta.validation.constraints.NotBlank;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class OrderRequestDTO {
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    @NotNull(message = "Restaurant ID is required")
    private Integer restaurantId;
    
    @NotNull(message = "Shipping Address ID is required")
    private Integer shippingAddressId;
    
    @NotNull(message = "Total amount is required")
    private Double totalAmount;
    
    @NotBlank(message = "Payment method is required")
    private String paymentMethod;
    
    private LocalDateTime estimatedDeliveryTime;
}
