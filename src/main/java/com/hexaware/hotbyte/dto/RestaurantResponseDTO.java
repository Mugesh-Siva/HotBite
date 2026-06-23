package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantResponseDTO {
    private Integer restaurantId;
    private Integer ownerUserId;
    private String restaurantName;
    private String contactNumber;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
