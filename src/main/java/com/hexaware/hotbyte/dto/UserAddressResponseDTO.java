package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressResponseDTO {
    private Integer addressId;
    private Integer userId;
    private String addressLine1;
    private String city;
    private String state;
    private String zipCode;
    private Boolean isDefault;
}
