package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.RestaurantRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantResponseDTO;
import com.hexaware.hotbyte.entity.Restaurant;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.repository.RestaurantRepository;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.service.RestaurantService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createRestaurant called with dto: {}", dto);
        Restaurant restaurant = new Restaurant();
        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setContactNumber(dto.getContactNumber());
        restaurant.setIsActive(dto.getIsActive());

        User owner = userRepository.findById(dto.getOwnerUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getOwnerUserId()));
        restaurant.setOwnerUser(owner);

        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return mapToDTO(savedRestaurant);
    }

    @Override
    public RestaurantResponseDTO updateRestaurant(Integer id, RestaurantRequestDTO dto) throws RestaurantNotFoundException, InvalidInputException {
        log.info("updateRestaurant called with id: {}, dto: {}", id, dto);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));

        restaurant.setRestaurantName(dto.getRestaurantName());
        restaurant.setContactNumber(dto.getContactNumber());
        restaurant.setIsActive(dto.getIsActive());

        User owner = userRepository.findById(dto.getOwnerUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getOwnerUserId()));
        restaurant.setOwnerUser(owner);

        Restaurant updatedRestaurant = restaurantRepository.save(restaurant);
        return mapToDTO(updatedRestaurant);
    }

    @Override
    public void deleteRestaurant(Integer id) throws RestaurantNotFoundException {
        log.info("deleteRestaurant called with id: {}", id);
        if (!restaurantRepository.existsById(id)) {
            throw new RestaurantNotFoundException("Restaurant not found with ID: " + id);
        }
        restaurantRepository.deleteById(id);
    }

    @Override
    public RestaurantResponseDTO getRestaurantById(Integer id) throws RestaurantNotFoundException {
        log.info("getRestaurantById called with id: {}", id);
        Restaurant restaurant = restaurantRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + id));
        return mapToDTO(restaurant);
    }

    @Override
    public List<RestaurantResponseDTO> getAllRestaurants() {
        log.info("getAllRestaurants called");
        return restaurantRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RestaurantResponseDTO mapToDTO(Restaurant restaurant) {
        RestaurantResponseDTO dto = new RestaurantResponseDTO();
        dto.setRestaurantId(restaurant.getRestaurantId());
        dto.setRestaurantName(restaurant.getRestaurantName());
        dto.setContactNumber(restaurant.getContactNumber());
        dto.setIsActive(restaurant.getIsActive());
        if(restaurant.getOwnerUser() != null) dto.setOwnerUserId(restaurant.getOwnerUser().getUserId());
        return dto;
    }
}
