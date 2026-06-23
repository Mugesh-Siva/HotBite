package com.hexaware.hotbyte.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class RestaurantRequestDTO {
    @NotNull(message = "Owner User ID is required")
    private Integer ownerUserId;
    
    @NotBlank(message = "Restaurant name is required")
    private String restaurantName;
    
    @NotBlank(message = "Contact number is required")
    private String contactNumber;
    
    private Boolean isActive;
}
