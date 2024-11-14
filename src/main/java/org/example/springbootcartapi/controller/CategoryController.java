package org.example.springbootcartapi.controller;

import lombok.RequiredArgsConstructor;
import org.example.springbootcartapi.exceptions.AlreadyExistsException;
import org.example.springbootcartapi.exceptions.ResourceNotFoundException;
import org.example.springbootcartapi.model.Category;
import org.example.springbootcartapi.response.APIResponse;
import org.example.springbootcartapi.services.category.ICategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {

    private final ICategoryService categoryService;

    @GetMapping("/getCategories")
    public ResponseEntity<APIResponse> getAllCategories() {
        try {
            List<Category> categories = categoryService.getAllCategories();
            return ResponseEntity.ok(new APIResponse("Found categories", categories));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/addCategory")
    public ResponseEntity<APIResponse> addCategory(@RequestBody Category categoryName) {
        try {
            Category category = categoryService.addCategory(categoryName);
            return ResponseEntity.ok(new APIResponse("Added category", category));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new APIResponse(e.getMessage(), null));
        }
    }


    @GetMapping("/getCategory/{id}")
    public ResponseEntity<APIResponse> getCategory(@PathVariable Long id) {
        try {
            Category category = categoryService.getCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Found category", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @GetMapping("/getCategoryByName/{categoryName}")
    public ResponseEntity<APIResponse> getCategoryByName(@PathVariable String categoryName) {
        try {
            Category category = categoryService.getCategoryByName(categoryName);
            return ResponseEntity.ok(new APIResponse("Found category", category));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteCategory/{id}")
    public ResponseEntity<APIResponse> deleteCategory(@PathVariable Long id) {
        try {
            categoryService.deleteCategoryById(id);
            return ResponseEntity.ok(new APIResponse("Deleted category", categoryService.getCategoryById(id)));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse(e.getMessage(), null));
        }
    }

}
