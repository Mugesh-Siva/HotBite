package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.OrderItemRequestDTO;
import com.hexaware.hotbyte.dto.OrderItemResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface OrderItemService {
    OrderItemResponseDTO createOrderItem(OrderItemRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    OrderItemResponseDTO updateOrderItem(Integer id, OrderItemRequestDTO dto) throws OrderItemNotFoundException, InvalidInputException;
    void deleteOrderItem(Integer id) throws OrderItemNotFoundException;
    OrderItemResponseDTO getOrderItemById(Integer id) throws OrderItemNotFoundException;
    List<OrderItemResponseDTO> getAllOrderItems();
}
