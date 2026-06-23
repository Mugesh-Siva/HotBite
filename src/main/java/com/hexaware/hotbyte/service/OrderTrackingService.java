package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.OrderTrackingRequestDTO;
import com.hexaware.hotbyte.dto.OrderTrackingResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface OrderTrackingService {
    OrderTrackingResponseDTO createOrderTracking(OrderTrackingRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    OrderTrackingResponseDTO updateOrderTracking(Integer id, OrderTrackingRequestDTO dto) throws OrderTrackingNotFoundException, InvalidInputException;
    void deleteOrderTracking(Integer id) throws OrderTrackingNotFoundException;
    OrderTrackingResponseDTO getOrderTrackingById(Integer id) throws OrderTrackingNotFoundException;
    List<OrderTrackingResponseDTO> getAllOrderTrackings();
}
