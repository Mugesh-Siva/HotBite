package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.OrderItemRequestDTO;
import com.hexaware.hotbyte.dto.OrderItemResponseDTO;
import com.hexaware.hotbyte.entity.MenuItem;
import com.hexaware.hotbyte.entity.Order;
import com.hexaware.hotbyte.entity.OrderItem;
import com.hexaware.hotbyte.repository.MenuItemRepository;
import com.hexaware.hotbyte.repository.OrderItemRepository;
import com.hexaware.hotbyte.repository.OrderRepository;
import com.hexaware.hotbyte.service.OrderItemService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderItemServiceImpl implements OrderItemService {

    @Autowired
    OrderItemRepository orderItemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    MenuItemRepository menuItemRepository;

    @Override
    public OrderItemResponseDTO createOrderItem(OrderItemRequestDTO dto) {
        log.info("createOrderItem called with dto: {}", dto);
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(dto.getQuantity());
        if (dto.getPurchasedPrice() != null) orderItem.setPurchasedPrice(BigDecimal.valueOf(dto.getPurchasedPrice()));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + dto.getOrderId()));
        orderItem.setOrder(order);

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + dto.getMenuItemId()));
        orderItem.setMenuItem(menuItem);

        OrderItem savedItem = orderItemRepository.save(orderItem);
        return mapToDTO(savedItem);
    }

    @Override
    public OrderItemResponseDTO updateOrderItem(Integer id, OrderItemRequestDTO dto) {
        log.info("updateOrderItem called with id: {}, dto: {}", id, dto);
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with ID: " + id));

        orderItem.setQuantity(dto.getQuantity());
        if (dto.getPurchasedPrice() != null) orderItem.setPurchasedPrice(BigDecimal.valueOf(dto.getPurchasedPrice()));

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + dto.getOrderId()));
        orderItem.setOrder(order);

        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .orElseThrow(() -> new MenuItemNotFoundException("Menu Item not found with ID: " + dto.getMenuItemId()));
        orderItem.setMenuItem(menuItem);

        OrderItem updatedItem = orderItemRepository.save(orderItem);
        return mapToDTO(updatedItem);
    }

    @Override
    public void deleteOrderItem(Integer id) {
        log.info("deleteOrderItem called with id: {}", id);
        if (!orderItemRepository.existsById(id)) {
            throw new OrderItemNotFoundException("OrderItem not found with ID: " + id);
        }
        orderItemRepository.deleteById(id);
    }

    @Override
    public OrderItemResponseDTO getOrderItemById(Integer id) {
        log.info("getOrderItemById called with id: {}", id);
        OrderItem orderItem = orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException("OrderItem not found with ID: " + id));
        return mapToDTO(orderItem);
    }

    @Override
    public List<OrderItemResponseDTO> getAllOrderItems() {
        log.info("getAllOrderItems called");
        return orderItemRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderItemResponseDTO mapToDTO(OrderItem item) {
        OrderItemResponseDTO dto = new OrderItemResponseDTO();
        dto.setOrderItemId(item.getOrderItemId());
        dto.setQuantity(item.getQuantity());
        if (item.getPurchasedPrice() != null) dto.setPurchasedPrice(item.getPurchasedPrice().doubleValue());
        if(item.getOrder() != null) dto.setOrderId(item.getOrder().getOrderId());
        if(item.getMenuItem() != null) dto.setMenuItemId(item.getMenuItem().getMenuItemId());
        return dto;
    }
}
