package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.CartItemRequestDTO;
import com.hexaware.hotbyte.dto.CartItemResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface CartItemService {
    CartItemResponseDTO createCartItem(CartItemRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    CartItemResponseDTO updateCartItem(Integer id, CartItemRequestDTO dto) throws CartItemNotFoundException, InvalidInputException;
    void deleteCartItem(Integer id) throws CartItemNotFoundException;
    CartItemResponseDTO getCartItemById(Integer id) throws CartItemNotFoundException;
    List<CartItemResponseDTO> getAllCartItems();
}
