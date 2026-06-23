package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemResponseDTO {
    private Integer cartItemId;
    private Integer cartId;
    private Integer menuItemId;
    private Integer quantity;
    private Double unitPrice;
}
