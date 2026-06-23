package com.hexaware.hotbyte.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotbyte.dto.CartRequestDTO;
import com.hexaware.hotbyte.dto.CartResponseDTO;
import com.hexaware.hotbyte.dto.UserRequestDTO;
import com.hexaware.hotbyte.dto.UserResponseDTO;
import com.hexaware.hotbyte.service.CartService;
import com.hexaware.hotbyte.service.UserService;

@SpringBootTest
class CartServiceImplTest {

	@Autowired
	CartService service;

	@Autowired
	UserService userService;

	@Autowired
	CartRequestDTO cartDTO;

	@Autowired
	UserRequestDTO userDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddCart() {

		userDTO.setFullName("Cart Test User");
		userDTO.setEmail("cartuser" + System.currentTimeMillis() + "@example.com");
		userDTO.setPassword("password123");
		userDTO.setRoleId(2);
		UserResponseDTO savedUser = userService.createUser(userDTO);

		cartDTO.setUserId(savedUser.getUserId()); 

		CartResponseDTO cart = service.createCart(cartDTO);

		assertNotNull(cart);
		assertEquals(savedUser.getUserId(), cart.getUserId());

	}

	@Test
	void testGetByCartId() {

		CartResponseDTO cart = null;
		try {
			cart = service.getCartById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(cart);
		assertTrue(cart.getUserId() > 0);

	}

	@Test
	void testGetAllCarts() {
		
		List<CartResponseDTO> list = service.getAllCarts();
		
		assertTrue(list.size() > 0);

	}

}
