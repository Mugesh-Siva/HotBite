package com.hexaware.hotbyte.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hexaware.hotbyte.dto.OrderRequestDTO;
import com.hexaware.hotbyte.dto.OrderResponseDTO;
import com.hexaware.hotbyte.service.OrderService;

@SpringBootTest
class OrderServiceImplTest {

	@Autowired
	OrderService service;

	@Autowired
	OrderRequestDTO orderDTO;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@Test
	void testAddOrder() {

		orderDTO.setUserId(2); 
		orderDTO.setRestaurantId(1); 
		orderDTO.setShippingAddressId(1);
		orderDTO.setTotalAmount(45.50);
		orderDTO.setPaymentMethod("Credit Card");

		OrderResponseDTO order = service.createOrder(orderDTO);

		assertNotNull(order);
		assertEquals(45.50, order.getTotalAmount());

	}

	@Test
	void testGetByOrderId() {

		OrderResponseDTO order = null;
		try {
			order = service.getOrderById(1); 
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertNotNull(order);
		assertTrue(order.getTotalAmount() > 0);

	}

	@Test
	void testGetAllOrders() {
		
		List<OrderResponseDTO> list = service.getAllOrders();
		
		assertTrue(list.size() > 0);

	}

}
