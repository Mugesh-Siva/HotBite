package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.CategoryRequestDTO;
import com.hexaware.hotbyte.dto.CategoryResponseDTO;
import com.hexaware.hotbyte.entity.Category;
import com.hexaware.hotbyte.repository.CategoryRepository;
import com.hexaware.hotbyte.service.CategoryService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public CategoryResponseDTO createCategory(CategoryRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createCategory called with dto: {}", dto);
        Category category = new Category();
        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());
        
        Category savedCategory = categoryRepository.save(category);
        return mapToDTO(savedCategory);
    }

    @Override
    public CategoryResponseDTO updateCategory(Integer id, CategoryRequestDTO dto) throws CategoryNotFoundException, InvalidInputException {
        log.info("updateCategory called with id: {}, dto: {}", id, dto);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));

        category.setCategoryName(dto.getCategoryName());
        category.setDescription(dto.getDescription());

        Category updatedCategory = categoryRepository.save(category);
        return mapToDTO(updatedCategory);
    }

    @Override
    public void deleteCategory(Integer id) throws CategoryNotFoundException {
        log.info("deleteCategory called with id: {}", id);
        if (!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found with ID: " + id);
        }
        categoryRepository.deleteById(id);
    }

    @Override
    public CategoryResponseDTO getCategoryById(Integer id) throws CategoryNotFoundException {
        log.info("getCategoryById called with id: {}", id);
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + id));
        return mapToDTO(category);
    }

    @Override
    public List<CategoryResponseDTO> getAllCategories() {
        log.info("getAllCategories called");
        return categoryRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CategoryResponseDTO mapToDTO(Category category) {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setCategoryId(category.getCategoryId());
        dto.setCategoryName(category.getCategoryName());
        dto.setDescription(category.getDescription());
        return dto;
    }
}
