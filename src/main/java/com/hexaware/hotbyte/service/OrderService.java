package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.OrderRequestDTO;
import com.hexaware.hotbyte.dto.OrderResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    OrderResponseDTO updateOrder(Integer id, OrderRequestDTO dto) throws OrderNotFoundException, InvalidInputException;
    void deleteOrder(Integer id) throws OrderNotFoundException;
    OrderResponseDTO getOrderById(Integer id) throws OrderNotFoundException;
    List<OrderResponseDTO> getAllOrders();
}
