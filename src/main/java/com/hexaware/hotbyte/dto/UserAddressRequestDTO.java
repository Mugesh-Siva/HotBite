package com.hexaware.hotbyte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressRequestDTO {
    @NotNull(message = "User ID is required")
    private Integer userId;
    
    @NotBlank(message = "Address Line 1 is required")
    private String addressLine1;
    
    @NotBlank(message = "City is required")
    private String city;
    
    private String state;
    private String zipCode;
    private Boolean isDefault;
}
