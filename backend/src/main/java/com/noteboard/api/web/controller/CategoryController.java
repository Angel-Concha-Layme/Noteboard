package com.noteboard.api.web.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.noteboard.api.dto.request.CategoryRequestDTO;
import com.noteboard.api.dto.response.CategoryResponseDTO;
import com.noteboard.api.exception.CategoryAlreadyExistsException;
import com.noteboard.api.service.CategoryService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@AllArgsConstructor
public class CategoryController {
    
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Object> createCategory(@RequestBody CategoryRequestDTO categoryRequestDTO) {
        try {
            CategoryResponseDTO categoryResponseDTO = categoryService.createCategory(categoryRequestDTO);
            return ResponseEntity.ok(categoryResponseDTO);
        } catch (CategoryAlreadyExistsException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body("Error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        List<CategoryResponseDTO> categories = categoryService.getAllCategories();
        return ResponseEntity.ok(categories);
    }
}


