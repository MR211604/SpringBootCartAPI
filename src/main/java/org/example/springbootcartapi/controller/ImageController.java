package org.example.springbootcartapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootcartapi.dto.ImageDTO;
import org.example.springbootcartapi.exceptions.ResourceNotFoundException;
import org.example.springbootcartapi.model.Image;
import org.example.springbootcartapi.response.APIResponse;
import org.example.springbootcartapi.services.image.IImageService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/images")
public class ImageController {

    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImages(
            @RequestParam List<MultipartFile> files,
            @RequestParam Long productId) {
        try {
            List<ImageDTO> imageDTOs = imageService.saveImage(files, productId);
            return ResponseEntity.ok(new APIResponse("Successfull upload", imageDTOs));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Error", e.getMessage()));
        }
    }

    @GetMapping("/download/${imageId}")
    public ResponseEntity<Resource> downloadImage(@RequestParam Long imageId) throws SQLException {
        Image image = imageService.getImageById(imageId);
        ByteArrayResource resource = new ByteArrayResource(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType
                        .parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + image.getFileName() + "\"")
                .body(resource);
    }

    @PutMapping("/update/${imageId}")
    public ResponseEntity<APIResponse> updateImage(@RequestParam Long imageId, @RequestParam MultipartFile files) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.updateImage(files, imageId);
                return ResponseEntity.ok(new APIResponse("Successfully updated", imageId));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Image not found", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Image update failed!", null));
    }


    @DeleteMapping("/delete/${imageId}")
    public ResponseEntity<APIResponse> deleteImage(@RequestParam Long imageId) {
        try {
            Image image = imageService.getImageById(imageId);
            if (image != null) {
                imageService.deleteImageById(imageId);
                return ResponseEntity.ok(new APIResponse("Successfully deleted", null));
            }
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Image not found", e.getMessage()));
        }
        return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new APIResponse("Image deletion failed!", null));
    }
}
