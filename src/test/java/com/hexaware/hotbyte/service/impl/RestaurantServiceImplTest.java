package com.hexaware.hotbyte.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotbyte.dto.RestaurantRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantResponseDTO;
import com.hexaware.hotbyte.service.RestaurantService;

@SpringBootTest
class RestaurantServiceImplTest {

	@Autowired
	RestaurantService service;

	@Autowired
	RestaurantRequestDTO restaurantDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddRestaurant() {

		restaurantDTO.setRestaurantName("The Test Cafe");
		restaurantDTO.setContactNumber("9876543210");
		restaurantDTO.setOwnerUserId(3); 
		restaurantDTO.setIsActive(true);

		RestaurantResponseDTO restaurant = null;
		try {
			restaurant = service.createRestaurant(restaurantDTO);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(restaurant);
		assertEquals("The Test Cafe", restaurant.getRestaurantName());

	}

	@Test
	void testGetByRestaurantId() {

		RestaurantResponseDTO restaurant = null;
		try {
			restaurant = service.getRestaurantById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(restaurant);
		assertTrue(restaurant.getRestaurantId() > 0);

	}

	@Test
	void testGetAllRestaurants() {
		
		List<RestaurantResponseDTO> list = service.getAllRestaurants();
		
		assertTrue(list.size() > 0);

	}

}
