package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.MenuImageDTO;
import com.hexaware.hotbyte.dto.MenuItemRequestDTO;
import com.hexaware.hotbyte.dto.MenuItemResponseDTO;
import com.hexaware.hotbyte.entity.Category;
import com.hexaware.hotbyte.entity.MenuImage;
import com.hexaware.hotbyte.entity.MenuItem;
import com.hexaware.hotbyte.entity.Restaurant;
import com.hexaware.hotbyte.repository.CategoryRepository;
import com.hexaware.hotbyte.repository.MenuImageRepository;
import com.hexaware.hotbyte.repository.MenuItemRepository;
import com.hexaware.hotbyte.repository.RestaurantRepository;
import com.hexaware.hotbyte.service.FileStorageService;
import com.hexaware.hotbyte.service.MenuItemService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MenuItemServiceImpl implements MenuItemService {

    @Autowired
    MenuItemRepository menuItemRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    CategoryRepository categoryRepository;
    
    @Autowired
    MenuImageRepository menuImageRepository;
    
    @Autowired
    FileStorageService fileStorageService;

    @Override
    public MenuItemResponseDTO createMenuItem(MenuItemRequestDTO dto) {
        log.info("createMenuItem called with dto: {}", dto);
        MenuItem menuItem = new MenuItem();
        menuItem.setItemName(dto.getItemName());
        menuItem.setDescription(dto.getDescription());
        if (dto.getPrice() != null) menuItem.setPrice(BigDecimal.valueOf(dto.getPrice()));
        if (dto.getDiscountPrice() != null) menuItem.setDiscountPrice(BigDecimal.valueOf(dto.getDiscountPrice()));
        menuItem.setAvailabilityTime(dto.getAvailabilityTime());
        menuItem.setDietaryInfo(dto.getDietaryInfo());
        menuItem.setTasteInfo(dto.getTasteInfo());
        menuItem.setNutritionalInfo(dto.getNutritionalInfo());
        menuItem.setIsOutOfStock(dto.getIsOutOfStock());

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));
        menuItem.setRestaurant(restaurant);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + dto.getCategoryId()));
        menuItem.setCategory(category);

        MenuItem savedItem = menuItemRepository.save(menuItem);
        return mapToDTO(savedItem);
    }

    @Override
    public MenuItemResponseDTO updateMenuItem(Integer id, MenuItemRequestDTO dto) {
        log.info("updateMenuItem called with id: {}, dto: {}", id, dto);
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + id));

        menuItem.setItemName(dto.getItemName());
        menuItem.setDescription(dto.getDescription());
        if (dto.getPrice() != null) menuItem.setPrice(BigDecimal.valueOf(dto.getPrice()));
        if (dto.getDiscountPrice() != null) menuItem.setDiscountPrice(BigDecimal.valueOf(dto.getDiscountPrice()));
        menuItem.setAvailabilityTime(dto.getAvailabilityTime());
        menuItem.setDietaryInfo(dto.getDietaryInfo());
        menuItem.setTasteInfo(dto.getTasteInfo());
        menuItem.setNutritionalInfo(dto.getNutritionalInfo());
        menuItem.setIsOutOfStock(dto.getIsOutOfStock());

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));
        menuItem.setRestaurant(restaurant);

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException("Category not found with ID: " + dto.getCategoryId()));
        menuItem.setCategory(category);

        MenuItem updatedItem = menuItemRepository.save(menuItem);
        return mapToDTO(updatedItem);
    }

    @Override
    public void deleteMenuItem(Integer id) {
        log.info("deleteMenuItem called with id: {}", id);
        if (!menuItemRepository.existsById(id)) {
            throw new MenuItemNotFoundException("Menu Item not found with ID: " + id);
        }
        menuItemRepository.deleteById(id);
    }

    @Override
    public MenuItemResponseDTO getMenuItemById(Integer id) {
        log.info("getMenuItemById called with id: {}", id);
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + id));
        return mapToDTO(menuItem);
    }

    @Override
    public List<MenuItemResponseDTO> getAllMenuItems() {
        log.info("getAllMenuItems called");
        return menuItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public MenuItemResponseDTO uploadImages(Integer id, List<MultipartFile> files) {
        log.info("uploadImages called for menuItemId: {}, files count: {}", id, files.size());
        if (files.size() > 5) {
            throw new InvalidInputException("Maximum 5 images allowed");
        }
        
        MenuItem menuItem = menuItemRepository.findById(id)
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + id));

        int order = menuItem.getImages() != null ? menuItem.getImages().size() + 1 : 1;
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                String fileUrl = fileStorageService.storeFile(file);
                MenuImage menuImage = new MenuImage();
                menuImage.setMenuItem(menuItem);
                menuImage.setImageUrl(fileUrl);
                menuImage.setDisplayOrder(order++);
                menuImageRepository.save(menuImage);
            }
        }
        
        // Fetch updated item to get images mapped correctly
        return mapToDTO(menuItemRepository.findById(id).get());
    }

    @Override
    @Transactional
    public void deleteMenuImage(Integer imageId) {
        log.info("deleteMenuImage called with imageId: {}", imageId);
        menuImageRepository.deleteById(imageId);
    }

    private MenuItemResponseDTO mapToDTO(MenuItem item) {
        MenuItemResponseDTO dto = new MenuItemResponseDTO();
        dto.setMenuItemId(item.getMenuItemId());
        dto.setItemName(item.getItemName());
        dto.setDescription(item.getDescription());
        if (item.getPrice() != null) dto.setPrice(item.getPrice().doubleValue());
        if (item.getDiscountPrice() != null) dto.setDiscountPrice(item.getDiscountPrice().doubleValue());
        dto.setAvailabilityTime(item.getAvailabilityTime());
        dto.setDietaryInfo(item.getDietaryInfo());
        dto.setTasteInfo(item.getTasteInfo());
        dto.setNutritionalInfo(item.getNutritionalInfo());
        dto.setIsOutOfStock(item.getIsOutOfStock());
        if (item.getRestaurant() != null) dto.setRestaurantId(item.getRestaurant().getRestaurantId());
        if (item.getCategory() != null) dto.setCategoryId(item.getCategory().getCategoryId());
        
        if (item.getImages() != null) {
            List<MenuImageDTO> imageDTOs = item.getImages().stream().map(img -> {
                MenuImageDTO imgDto = new MenuImageDTO();
                imgDto.setImageId(img.getImageId());
                imgDto.setImageUrl(img.getImageUrl());
                imgDto.setDisplayOrder(img.getDisplayOrder());
                return imgDto;
            }).collect(Collectors.toList());
            dto.setImages(imageDTOs);
        }
        
        return dto;
    }
}
