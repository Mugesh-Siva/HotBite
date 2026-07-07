package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemResponseDTO {
    private Integer menuItemId;
    private Integer restaurantId;
    private Integer categoryId;
    private String itemName;
    private String description;
    private Double price;
    private Double discountPrice;
    private String availabilityTime;
    private String dietaryInfo;
    private String tasteInfo;
    private String nutritionalInfo;
    private Boolean isOutOfStock;
    private LocalDateTime createdAt;
    private List<MenuImageDTO> images;
}
