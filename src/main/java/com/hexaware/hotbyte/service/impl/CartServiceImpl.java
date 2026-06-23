package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.CartRequestDTO;
import com.hexaware.hotbyte.dto.CartResponseDTO;
import com.hexaware.hotbyte.entity.Cart;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.repository.CartRepository;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.service.CartService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    CartRepository cartRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public CartResponseDTO createCart(CartRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createCart called with dto: {}", dto);
        Cart cart = new Cart();

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getUserId()));
        cart.setUser(user);

        Cart savedCart = cartRepository.save(cart);
        return mapToDTO(savedCart);
    }

    @Override
    public CartResponseDTO updateCart(Integer id, CartRequestDTO dto) throws CartNotFoundException, InvalidInputException {
        log.info("updateCart called with id: {}, dto: {}", id, dto);
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + id));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getUserId()));
        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);
        return mapToDTO(updatedCart);
    }

    @Override
    public void deleteCart(Integer id) throws CartNotFoundException {
        log.info("deleteCart called with id: {}", id);
        if (!cartRepository.existsById(id)) {
            throw new CartNotFoundException("Cart not found with ID: " + id);
        }
        cartRepository.deleteById(id);
    }

    @Override
    public CartResponseDTO getCartById(Integer id) throws CartNotFoundException {
        log.info("getCartById called with id: {}", id);
        Cart cart = cartRepository.findById(id)
                .orElseThrow(() -> new CartNotFoundException("Cart not found with ID: " + id));
        return mapToDTO(cart);
    }

    @Override
    public List<CartResponseDTO> getAllCarts() {
        log.info("getAllCarts called");
        return cartRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CartResponseDTO mapToDTO(Cart cart) {
        CartResponseDTO dto = new CartResponseDTO();
        dto.setCartId(cart.getCartId());
        if(cart.getUser() != null) {
            dto.setUserId(cart.getUser().getUserId());
        }
        return dto;
    }
}
