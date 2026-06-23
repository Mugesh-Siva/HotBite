package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Integer orderItemId;
    private Integer orderId;
    private Integer menuItemId;
    private Integer quantity;
    private Double purchasedPrice;
}
