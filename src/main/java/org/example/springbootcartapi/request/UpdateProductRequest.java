package org.example.springbootcartapi.request;
import lombok.Data;
import org.example.springbootcartapi.model.Category;
import java.math.BigDecimal;

@Data
public class UpdateProductRequest {
    private String name;
    private String brand;
    private String description;
    private BigDecimal price;
    private int stock;
    private int quantity;
    private Category category;
}
