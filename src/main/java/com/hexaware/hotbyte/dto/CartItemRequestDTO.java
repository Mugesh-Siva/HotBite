package com.hexaware.hotbyte.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemRequestDTO {
    @NotNull(message = "Cart ID is required")
    private Integer cartId;
    
    @NotNull(message = "Menu Item ID is required")
    private Integer menuItemId;
    
    @NotNull(message = "Quantity is required")
    private Integer quantity;
    
    @NotNull(message = "Unit price is required")
    private Double unitPrice;
}
