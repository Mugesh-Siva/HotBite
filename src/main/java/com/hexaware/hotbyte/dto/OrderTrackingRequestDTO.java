package com.hexaware.hotbyte.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingRequestDTO {
    @NotNull(message = "Order ID is required")
    private Integer orderId;
    
    @NotBlank(message = "Status update is required")
    private String statusUpdate;
    
    private String description;
}
