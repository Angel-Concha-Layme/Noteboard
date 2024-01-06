package com.noteboard.api.service;

import org.springframework.stereotype.Service;

import com.noteboard.api.dto.request.CategoryRequestDTO;
import com.noteboard.api.dto.response.CategoryResponseDTO;
import com.noteboard.api.exception.CategoryAlreadyExistsException;
import com.noteboard.api.exception.CategoryNotFoundException;
import com.noteboard.api.persistence.entity.Category;
import com.noteboard.api.persistence.repository.CategoryRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) {
        if (categoryExists(categoryRequestDTO.name())) {
            throw new CategoryAlreadyExistsException("Una categoría con el nombre '" + categoryRequestDTO.name() + "' ya existe.");
        }

        Category createdCategory = new Category();
        createdCategory.setName(categoryRequestDTO.name());
        createdCategory = categoryRepository.save(createdCategory);
        return CategoryResponseDTO.builder()
                .id(createdCategory.getId())
                .name(createdCategory.getName())
                .build();
    }

    public void deleteCategory(Long id) {
        try{
            Category category = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException("La categoría con id " + id + " no existe."));
            categoryRepository.delete(category);
        } catch(NoSuchElementException e) {
            throw new CategoryNotFoundException(e.getMessage());
        }
    }

    private boolean categoryExists(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    public List<CategoryResponseDTO> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> CategoryResponseDTO.builder()
                        .id(category.getId())
                        .name(category.getName())
                        .build())
                .collect(Collectors.toList());
    }
}