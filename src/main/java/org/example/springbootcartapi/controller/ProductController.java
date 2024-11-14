package org.example.springbootcartapi.controller;
import lombok.RequiredArgsConstructor;
import org.example.springbootcartapi.exceptions.ResourceNotFoundException;
import org.example.springbootcartapi.model.Product;
import org.example.springbootcartapi.request.AddProductRequest;
import org.example.springbootcartapi.request.UpdateProductRequest;
import org.example.springbootcartapi.response.APIResponse;
import org.example.springbootcartapi.services.product.IProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {

    private final IProductService productService;

    @GetMapping("/getProducts")
    ResponseEntity<APIResponse> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(new APIResponse("success", products));
    }

    @GetMapping("/getProductById/{id}")
    ResponseEntity<APIResponse> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(new APIResponse("product found", product));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/addProduct")
    ResponseEntity<APIResponse> addProduct(@RequestBody AddProductRequest product) {
        Product addedProduct = productService.addProduct(product);
        return ResponseEntity.ok(new APIResponse("product added successfully", addedProduct));
    }

    @PutMapping("/updateProduct/{productId}")
    ResponseEntity<APIResponse> updateProduct(@RequestBody UpdateProductRequest product, @PathVariable Long productId) {
        try {
            Product updatedProduct = productService.updateProduct(product, productId);
            return ResponseEntity.ok(new APIResponse("product updated successfully", updatedProduct));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));

        }
    }

    @DeleteMapping("/deleteProduct/{productId}")
    ResponseEntity<APIResponse> deleteProduct(@PathVariable Long productId) {
        try {
            productService.deleteProductById(productId);
            return ResponseEntity.ok(new APIResponse("product deleted successfully", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/productsByName")
    ResponseEntity<APIResponse> getProductsByName(String name) {
        try {
            List<Product> products = productService.getProductsByName(name);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("No products found", null));
            }
            return ResponseEntity.ok(new APIResponse("Found products", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/productsByBrand")
    ResponseEntity<APIResponse> getProductsByBrand(@RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByBrand(brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("No products found", null));
            }
            return ResponseEntity.ok(new APIResponse("Found products", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/productsByCategory")
    ResponseEntity<APIResponse> getProductsByCategory(@RequestParam String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("No products found", null));
            }
            return ResponseEntity.ok(new APIResponse("Found products", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/productsByCategoryAndBrand")
    ResponseEntity<APIResponse> getProductsByCategoryAndBrand(@RequestParam String category, @RequestParam String brand) {
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(category, brand);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("No products found", null));
            }
            return ResponseEntity.ok(new APIResponse("Found products", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/productsByBrandAndName")
    ResponseEntity<APIResponse> getProductsByBrandAndName(@RequestParam String brand, @RequestParam String name) {
        try {
            List<Product> products = productService.getProductsByBrandAndName(brand, name);
            if(products.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("No products found", null));
            }
            return ResponseEntity.ok(new APIResponse("Found products", products));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse(e.getMessage(), null));
        }
    }
}
