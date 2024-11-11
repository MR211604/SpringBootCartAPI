package org.example.springbootcartapi.services.image;
import lombok.RequiredArgsConstructor;
import org.example.springbootcartapi.dto.ImageDTO;
import org.example.springbootcartapi.exceptions.ResourceNotFoundException;
import org.example.springbootcartapi.model.Image;
import org.example.springbootcartapi.model.Product;
import org.example.springbootcartapi.repository.ImageRepository;
import org.example.springbootcartapi.repository.ProductRepository;
import org.example.springbootcartapi.services.product.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {

    private final ImageRepository imageRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @Override
    public Image getImageById(Long id) {
        return imageRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No image found with id: " + id));
    }

    @Override
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }

    @Override
    public List<ImageDTO> saveImage(List<MultipartFile> files, Long productId) {
        String URL = "/api/v1/images/download";
        Product product = productService.getProductById(productId);
        List<ImageDTO> savedImageDTO = new ArrayList<>();
        for(MultipartFile file : files) {
            try {
                Image image = new Image();
                image.setFileName(file.getOriginalFilename());
                image.setFileType(file.getContentType());
                image.setProduct(product);
                String downloadURL = URL + image.getId();
                image.setDownloadURL(downloadURL);

                //Update the URL no the new ID generated
                Image savedImage = imageRepository.save(image);

                //Setting a new downloadURL
                savedImage.setDownloadURL(URL + savedImage.getId());
                imageRepository.save(savedImage);

                ImageDTO imageDTO = new ImageDTO();
                imageDTO.setImageId(savedImage.getId());
                imageDTO.setImageName(savedImage.getFileName());
                imageDTO.setDownloadURL(savedImage.getDownloadURL());
                savedImageDTO.add(imageDTO);

            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
        return savedImageDTO;
    }

    @Override
    public void updateImage(MultipartFile file, Long imageId) {
        Image image = getImageById(imageId);
        try {
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            imageRepository.save(image);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
