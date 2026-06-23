package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingResponseDTO {
    private Integer trackingId;
    private Integer orderId;
    private String statusUpdate;
    private String description;
    private LocalDateTime updatedAt;
}
