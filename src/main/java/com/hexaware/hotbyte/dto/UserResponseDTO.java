package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDTO {
    private Integer userId;
    private Integer roleId;
    private String roleName;
    private String fullName;
    private String email;
    private String contactNumber;
    private String gender;
    private Boolean isActive;
    private LocalDateTime createdAt;
}
