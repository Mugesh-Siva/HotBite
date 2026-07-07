package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.OrderRequestDTO;
import com.hexaware.hotbyte.dto.OrderResponseDTO;
import com.hexaware.hotbyte.entity.Order;
import com.hexaware.hotbyte.entity.Restaurant;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.entity.UserAddress;
import com.hexaware.hotbyte.repository.OrderRepository;
import com.hexaware.hotbyte.repository.RestaurantRepository;
import com.hexaware.hotbyte.repository.UserAddressRepository;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.service.OrderService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserAddressRepository userAddressRepository;

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO dto) {
        log.info("createOrder called with dto: {}", dto);
        Order order = new Order();
        if (dto.getTotalAmount() != null) order.setTotalAmount(BigDecimal.valueOf(dto.getTotalAmount()));
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setEstimatedDeliveryTime(dto.getEstimatedDeliveryTime());
        order.setOrderStatus(dto.getOrderStatus() != null ? dto.getOrderStatus() : "Pending");
        order.setCreatedAt(java.time.LocalDateTime.now());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getUserId()));
        order.setUser(user);

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));
        order.setRestaurant(restaurant);

        if(dto.getShippingAddressId() != null) {
            UserAddress address = userAddressRepository.findById(dto.getShippingAddressId())
                    .orElseThrow(() -> new InvalidInputException("Address not found with ID: " + dto.getShippingAddressId()));
            order.setShippingAddress(address);
        }

        Order savedOrder = orderRepository.save(order);
        return mapToDTO(savedOrder);
    }

    @Override
    public OrderResponseDTO updateOrder(Integer id, OrderRequestDTO dto) {
        log.info("updateOrder called with id: {}, dto: {}", id, dto);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));

        if (dto.getTotalAmount() != null) order.setTotalAmount(BigDecimal.valueOf(dto.getTotalAmount()));
        order.setPaymentMethod(dto.getPaymentMethod());
        order.setEstimatedDeliveryTime(dto.getEstimatedDeliveryTime());
        if (dto.getOrderStatus() != null) {
            order.setOrderStatus(dto.getOrderStatus());
        }

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getUserId()));
        order.setUser(user);

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));
        order.setRestaurant(restaurant);

        if(dto.getShippingAddressId() != null) {
            UserAddress address = userAddressRepository.findById(dto.getShippingAddressId())
                    .orElseThrow(() -> new InvalidInputException("Address not found with ID: " + dto.getShippingAddressId()));
            order.setShippingAddress(address);
        } else {
            order.setShippingAddress(null);
        }

        Order updatedOrder = orderRepository.save(order);
        return mapToDTO(updatedOrder);
    }

    @Override
    public void deleteOrder(Integer id) {
        log.info("deleteOrder called with id: {}", id);
        if (!orderRepository.existsById(id)) {
            throw new OrderNotFoundException("Order not found with ID: " + id);
        }
        orderRepository.deleteById(id);
    }

    @Override
    public OrderResponseDTO getOrderById(Integer id) {
        log.info("getOrderById called with id: {}", id);
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + id));
        return mapToDTO(order);
    }

    @Override
    public List<OrderResponseDTO> getAllOrders() {
        log.info("getAllOrders called");
        return orderRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private OrderResponseDTO mapToDTO(Order order) {
        OrderResponseDTO dto = new OrderResponseDTO();
        dto.setOrderId(order.getOrderId());
        if (order.getTotalAmount() != null) dto.setTotalAmount(order.getTotalAmount().doubleValue());
        dto.setPaymentMethod(order.getPaymentMethod());
        dto.setEstimatedDeliveryTime(order.getEstimatedDeliveryTime());
        dto.setOrderStatus(order.getOrderStatus());
        dto.setCreatedAt(order.getCreatedAt());
        if(order.getUser() != null) dto.setUserId(order.getUser().getUserId());
        if(order.getRestaurant() != null) dto.setRestaurantId(order.getRestaurant().getRestaurantId());
        if(order.getShippingAddress() != null) dto.setShippingAddressId(order.getShippingAddress().getAddressId());
        return dto;
    }
}
