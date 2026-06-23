package com.hexaware.hotbyte.dto;

import org.springframework.stereotype.Component;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CartRequestDTO {
    @NotNull(message = "User ID is required")
    private Integer userId;
}
