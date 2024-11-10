package org.example.springbootcartapi.services.product;
import org.example.springbootcartapi.model.Product;
import org.example.springbootcartapi.request.AddProductRequest;
import org.example.springbootcartapi.request.UpdateProductRequest;

import java.util.List;

public interface IProductService {
    Product addProdcut(AddProductRequest product);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(UpdateProductRequest product, Long productId);
    List<Product> getAllProducts();

    //Selects (possibly)
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product> getProductsByCategoryAndBrand(String category, String brand);
    List<Product> getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String brand, String name);

    Long countProductsByBrandAndName(String brand, String name);
}
