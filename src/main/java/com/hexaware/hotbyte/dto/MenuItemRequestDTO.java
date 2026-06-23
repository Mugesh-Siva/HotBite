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
public class MenuItemRequestDTO {
    @NotNull(message = "Restaurant ID is required")
    private Integer restaurantId;
    
    @NotNull(message = "Category ID is required")
    private Integer categoryId;
    
    @NotBlank(message = "Item name is required")
    private String itemName;
    
    private String description;
    
    @NotNull(message = "Price is required")
    private Double price;
    
    private Double discountPrice;
    private String availabilityTime;
    private String dietaryInfo;
    private String tasteInfo;
    private String nutritionalInfo;
    private Boolean isOutOfStock;
}
