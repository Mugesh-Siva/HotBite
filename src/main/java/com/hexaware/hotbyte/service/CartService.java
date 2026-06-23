package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.CartRequestDTO;
import com.hexaware.hotbyte.dto.CartResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface CartService {
    CartResponseDTO createCart(CartRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    CartResponseDTO updateCart(Integer id, CartRequestDTO dto) throws CartNotFoundException, InvalidInputException;
    void deleteCart(Integer id) throws CartNotFoundException;
    CartResponseDTO getCartById(Integer id) throws CartNotFoundException;
    List<CartResponseDTO> getAllCarts();
}
