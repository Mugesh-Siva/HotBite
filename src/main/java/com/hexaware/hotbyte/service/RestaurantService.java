package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.RestaurantRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface RestaurantService {
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    RestaurantResponseDTO updateRestaurant(Integer id, RestaurantRequestDTO dto) throws RestaurantNotFoundException, InvalidInputException;
    void deleteRestaurant(Integer id) throws RestaurantNotFoundException;
    RestaurantResponseDTO getRestaurantById(Integer id) throws RestaurantNotFoundException;
    List<RestaurantResponseDTO> getAllRestaurants();
}
