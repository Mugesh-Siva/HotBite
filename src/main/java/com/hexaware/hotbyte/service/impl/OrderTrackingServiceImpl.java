package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.OrderTrackingRequestDTO;
import com.hexaware.hotbyte.dto.OrderTrackingResponseDTO;
import com.hexaware.hotbyte.entity.Order;
import com.hexaware.hotbyte.entity.OrderTracking;
import com.hexaware.hotbyte.repository.OrderRepository;
import com.hexaware.hotbyte.repository.OrderTrackingRepository;
import com.hexaware.hotbyte.service.OrderTrackingService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderTrackingServiceImpl implements OrderTrackingService {

    @Autowired
    OrderTrackingRepository orderTrackingRepository;

    @Autowired
    OrderRepository orderRepository;

    @Override
    public OrderTrackingResponseDTO createOrderTracking(OrderTrackingRequestDTO dto) {
        log.info("createOrderTracking called with dto: {}", dto);
        OrderTracking tracking = new OrderTracking();
        tracking.setStatusUpdate(dto.getStatusUpdate());
        tracking.setDescription(dto.getDescription());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + dto.getOrderId()));
        tracking.setOrder(order);

        OrderTracking savedTracking = orderTrackingRepository.save(tracking);
        return mapToDTO(savedTracking);
    }

    @Override
    public OrderTrackingResponseDTO updateOrderTracking(Integer id, OrderTrackingRequestDTO dto) {
        log.info("updateOrderTracking called with id: {}, dto: {}", id, dto);
        OrderTracking tracking = orderTrackingRepository.findById(id)
                .orElseThrow(() -> new OrderTrackingNotFoundException("OrderTracking not found with ID: " + id));

        tracking.setStatusUpdate(dto.getStatusUpdate());
        tracking.setDescription(dto.getDescription());

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + dto.getOrderId()));
        tracking.setOrder(order);

        OrderTracking updatedTracking = orderTrackingRepository.save(tracking);
        return mapToDTO(updatedTracking);
    }

    @Override
    public void deleteOrderTracking(Integer id) {
        log.info("deleteOrderTracking called with id: {}", id);
        if (!orderTrackingRepository.existsById(id)) {
            throw new OrderTrackingNotFoundException("OrderTracking not found with ID: " + id);
        }
        orderTrackingRepository.deleteById(id);
    }

    @Override
    public OrderTrackingResponseDTO getOrderTrackingById(Integer id) {
        log.info("getOrderTrackingById called with id: {}", id);
        OrderTracking tracking = orderTrackingRepository.findById(id)
                .orElseThrow(() -> new OrderTrackingNotFoundException("OrderTracking not found with ID: " + id));
        return mapToDTO(tracking);
    }

    @Override
    public List<OrderTrackingResponseDTO> getAllOrderTrackings() {
        log.info("getAllOrderTrackings called");
        return orderTrackingRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderTrackingResponseDTO mapToDTO(OrderTracking tracking) {
        OrderTrackingResponseDTO dto = new OrderTrackingResponseDTO();
        dto.setTrackingId(tracking.getTrackingId());
        dto.setStatusUpdate(tracking.getStatusUpdate());
        dto.setDescription(tracking.getDescription());
        if(tracking.getOrder() != null) dto.setOrderId(tracking.getOrder().getOrderId());
        return dto;
    }
}
