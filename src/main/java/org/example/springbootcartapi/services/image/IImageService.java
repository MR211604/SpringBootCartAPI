package org.example.springbootcartapi.services.image;

import org.example.springbootcartapi.dto.ImageDTO;
import org.example.springbootcartapi.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> files, Long productId);
    void updateImage(MultipartFile file, Long imageId);
}
