package com.hexaware.hotbyte.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotbyte.dto.MenuItemRequestDTO;
import com.hexaware.hotbyte.dto.MenuItemResponseDTO;
import com.hexaware.hotbyte.service.MenuItemService;

@SpringBootTest
class MenuItemServiceImplTest {

	@Autowired
	MenuItemService service;

	@Autowired
	MenuItemRequestDTO menuItemDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddMenuItem() {

		menuItemDTO.setRestaurantId(1);
		menuItemDTO.setCategoryId(1);
		menuItemDTO.setItemName("Test Pizza");
		menuItemDTO.setPrice(15.99);
		menuItemDTO.setIsOutOfStock(false);

		MenuItemResponseDTO item = service.createMenuItem(menuItemDTO);

		assertNotNull(item);
		assertEquals("Test Pizza", item.getItemName());

	}

	@Test
	void testGetByMenuItemId() {

		MenuItemResponseDTO item = null;
		try {
			item = service.getMenuItemById(1);
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(item);
		assertTrue(item.getPrice() > 0);

	}

	@Test
	void testGetAllMenuItems() {
		
		List<MenuItemResponseDTO> list = service.getAllMenuItems();
		
		assertTrue(list.size() > 0);

	}

}
