package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.CartItemRequestDTO;
import com.hexaware.hotbyte.dto.CartItemResponseDTO;
import com.hexaware.hotbyte.entity.Cart;
import com.hexaware.hotbyte.entity.CartItem;
import com.hexaware.hotbyte.entity.MenuItem;
import com.hexaware.hotbyte.repository.CartItemRepository;
import com.hexaware.hotbyte.repository.CartRepository;
import com.hexaware.hotbyte.repository.MenuItemRepository;
import com.hexaware.hotbyte.service.CartItemService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartItemServiceImpl implements CartItemService {

    @Autowired
    CartItemRepository cartItemRepository;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Override
    public CartItemResponseDTO createCartItem(CartItemRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createCartItem called with dto: {}", dto);
        CartItem cartItem = new CartItem();
        cartItem.setQuantity(dto.getQuantity());
        if (dto.getUnitPrice() != null) {
            cartItem.setUnitPrice(BigDecimal.valueOf(dto.getUnitPrice()));
        }

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + dto.getCartId()));
        cartItem.setCart(cart);

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + dto.getMenuItemId()));
        cartItem.setMenuItem(menuItem);

        CartItem savedItem = cartItemRepository.save(cartItem);
        return mapToDTO(savedItem);
    }

    @Override
    public CartItemResponseDTO updateCartItem(Integer id, CartItemRequestDTO dto) throws CartItemNotFoundException, InvalidInputException {
        log.info("updateCartItem called with id: {}, dto: {}", id, dto);
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with ID: " + id));

        cartItem.setQuantity(dto.getQuantity());
        if (dto.getUnitPrice() != null) {
            cartItem.setUnitPrice(BigDecimal.valueOf(dto.getUnitPrice()));
        }

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + dto.getCartId()));
        cartItem.setCart(cart);

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + dto.getMenuItemId()));
        cartItem.setMenuItem(menuItem);

        CartItem updatedItem = cartItemRepository.save(cartItem);
        return mapToDTO(updatedItem);
    }

    @Override
    public void deleteCartItem(Integer id) throws CartItemNotFoundException {
        log.info("deleteCartItem called with id: {}", id);
        if (!cartItemRepository.existsById(id)) {
            throw new CartItemNotFoundException("CartItem not found with ID: " + id);
        }
        cartItemRepository.deleteById(id);
    }

    @Override
    public CartItemResponseDTO getCartItemById(Integer id) throws CartItemNotFoundException {
        log.info("getCartItemById called with id: {}", id);
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new CartItemNotFoundException("CartItem not found with ID: " + id));
        return mapToDTO(cartItem);
    }

    @Override
    public List<CartItemResponseDTO> getAllCartItems() {
        log.info("getAllCartItems called");
        return cartItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CartItemResponseDTO mapToDTO(CartItem item) {
        CartItemResponseDTO dto = new CartItemResponseDTO();
        dto.setCartItemId(item.getCartItemId());
        dto.setQuantity(item.getQuantity());
        if(item.getUnitPrice() != null) {
            dto.setUnitPrice(item.getUnitPrice().doubleValue());
        }
        if(item.getCart() != null) dto.setCartId(item.getCart().getCartId());
        if(item.getMenuItem() != null) dto.setMenuItemId(item.getMenuItem().getMenuItemId());
        return dto;
    }
}
