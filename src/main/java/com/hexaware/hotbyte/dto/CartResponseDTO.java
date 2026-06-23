package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartResponseDTO {
    private Integer cartId;
    private Integer userId;
    private Double totalCost;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
