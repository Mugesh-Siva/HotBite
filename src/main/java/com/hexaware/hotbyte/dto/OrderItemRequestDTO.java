package com.hexaware.hotbyte.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDTO {
    @NotNull(message = "Order ID is required")
    private Integer orderId;
    
    @NotNull(message = "Menu Item ID is required")
    private Integer menuItemId;
    
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    
    @NotNull(message = "Purchased price is required")
    private Double purchasedPrice;
}
