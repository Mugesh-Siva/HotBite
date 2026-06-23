package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.CategoryRequestDTO;
import com.hexaware.hotbyte.dto.CategoryResponseDTO;
import com.hexaware.hotbyte.service.CategoryService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<CategoryResponseDTO> createCategory(@Valid @RequestBody CategoryRequestDTO dto) {
        log.info("createCategory endpoint called");
        CategoryResponseDTO response = categoryService.createCategory(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<CategoryResponseDTO> updateCategory(@PathVariable Integer id, @Valid @RequestBody CategoryRequestDTO dto) {
        log.info("updateCategory endpoint called with id: {}", id);
        CategoryResponseDTO response = categoryService.updateCategory(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<CategoryResponseDTO> getCategoryById(@PathVariable Integer id) {
        log.info("getCategoryById endpoint called with id: {}", id);
        CategoryResponseDTO response = categoryService.getCategoryById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<CategoryResponseDTO>> getAllCategories() {
        log.info("getAllCategories endpoint called");
        List<CategoryResponseDTO> response = categoryService.getAllCategories();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteCategoryById(@PathVariable Integer id) {
        log.info("deleteCategoryById endpoint called with id: {}", id);
        categoryService.deleteCategory(id);
        return new ResponseEntity<>("Category deleted successfully", HttpStatus.ACCEPTED);
    }
}
