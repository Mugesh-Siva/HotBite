package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.MenuItemRequestDTO;
import com.hexaware.hotbyte.dto.MenuItemResponseDTO;
import com.hexaware.hotbyte.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/menu-items")
public class MenuItemController {

    @Autowired
    MenuItemService menuItemService;

    @PostMapping("/add")
    public ResponseEntity<MenuItemResponseDTO> createMenuItem(@Valid @RequestBody MenuItemRequestDTO dto) {
        log.info("createMenuItem endpoint called");
        MenuItemResponseDTO response = menuItemService.createMenuItem(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<MenuItemResponseDTO> updateMenuItem(@PathVariable Integer id, @Valid @RequestBody MenuItemRequestDTO dto) {
        log.info("updateMenuItem endpoint called with id: {}", id);
        MenuItemResponseDTO response = menuItemService.updateMenuItem(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<MenuItemResponseDTO> getMenuItemById(@PathVariable Integer id) {
        log.info("getMenuItemById endpoint called with id: {}", id);
        MenuItemResponseDTO response = menuItemService.getMenuItemById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<MenuItemResponseDTO>> getAllMenuItems() {
        log.info("getAllMenuItems endpoint called");
        List<MenuItemResponseDTO> response = menuItemService.getAllMenuItems();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteMenuItemById(@PathVariable Integer id) {
        log.info("deleteMenuItemById endpoint called with id: {}", id);
        menuItemService.deleteMenuItem(id);
        return new ResponseEntity<>("Menu Item deleted successfully", HttpStatus.ACCEPTED);
    }

    @PostMapping("/{id}/images")
    public ResponseEntity<MenuItemResponseDTO> uploadImages(
            @PathVariable Integer id,
            @RequestParam("files") List<org.springframework.web.multipart.MultipartFile> files) {
        log.info("uploadImages endpoint called with id: {}, files: {}", id, files.size());
        MenuItemResponseDTO response = menuItemService.uploadImages(id, files);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Integer imageId) {
        log.info("deleteImage endpoint called with imageId: {}", imageId);
        menuItemService.deleteMenuImage(imageId);
        return new ResponseEntity<>("Image deleted successfully", HttpStatus.ACCEPTED);
    }
}
