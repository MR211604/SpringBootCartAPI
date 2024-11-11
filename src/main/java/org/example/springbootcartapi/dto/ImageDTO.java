package org.example.springbootcartapi.dto;

import lombok.Data;

@Data
public class ImageDTO {
    private Long imageId;
    private String imageName;
    private String downloadURL;
}
