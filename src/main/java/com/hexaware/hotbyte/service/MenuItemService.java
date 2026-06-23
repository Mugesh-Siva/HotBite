package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.MenuItemRequestDTO;
import com.hexaware.hotbyte.dto.MenuItemResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface MenuItemService {
    MenuItemResponseDTO createMenuItem(MenuItemRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    MenuItemResponseDTO updateMenuItem(Integer id, MenuItemRequestDTO dto) throws MenuItemNotFoundException, InvalidInputException;
    void deleteMenuItem(Integer id) throws MenuItemNotFoundException;
    MenuItemResponseDTO getMenuItemById(Integer id) throws MenuItemNotFoundException;
    List<MenuItemResponseDTO> getAllMenuItems();
}
