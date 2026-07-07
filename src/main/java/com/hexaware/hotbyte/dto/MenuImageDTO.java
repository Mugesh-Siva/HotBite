package com.hexaware.hotbyte.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuImageDTO {
    private Integer imageId;
    private String imageUrl;
    private Integer displayOrder;
}
