package com.hexaware.hotbyte.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotbyte.dto.UserRequestDTO;
import com.hexaware.hotbyte.dto.UserResponseDTO;
import com.hexaware.hotbyte.exception.UserNotFoundException;
import com.hexaware.hotbyte.service.UserService;

@SpringBootTest
class UserServiceImplTest {

	@Autowired
	UserService service;

	@Autowired
	UserRequestDTO userDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddUser() {

		userDTO.setFullName("Test User");
		userDTO.setEmail("testuser" + System.currentTimeMillis() + "@example.com");
		userDTO.setPassword("password123");
		userDTO.setRoleId(2); 

		UserResponseDTO user = service.createUser(userDTO);

		assertNotNull(user);
		assertEquals("Test User", user.getFullName());

	}

	@Test
	void testGetByUserId() {

		UserResponseDTO user = null;
		try {
			user = service.getUserById(1); 
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(user);
		assertTrue(user.getRoleId() > 0);

	}

	@Test
	void testGetAllUsers() {
		
		List<UserResponseDTO> list = service.getAllUsers();
		
		assertTrue(list.size() > 0);

	}

}
