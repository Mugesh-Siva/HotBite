package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.CategoryRequestDTO;
import com.hexaware.hotbyte.dto.CategoryResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface CategoryService {
    CategoryResponseDTO createCategory(CategoryRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    CategoryResponseDTO updateCategory(Integer id, CategoryRequestDTO dto) throws CategoryNotFoundException, InvalidInputException;
    void deleteCategory(Integer id) throws CategoryNotFoundException;
    CategoryResponseDTO getCategoryById(Integer id) throws CategoryNotFoundException;
    List<CategoryResponseDTO> getAllCategories();
}
