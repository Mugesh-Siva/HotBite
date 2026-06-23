package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.RestaurantAddressRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantAddressResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface RestaurantAddressService {
    RestaurantAddressResponseDTO createRestaurantAddress(RestaurantAddressRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    RestaurantAddressResponseDTO updateRestaurantAddress(Integer id, RestaurantAddressRequestDTO dto) throws RestaurantAddressNotFoundException, InvalidInputException;
    void deleteRestaurantAddress(Integer id) throws RestaurantAddressNotFoundException;
    RestaurantAddressResponseDTO getRestaurantAddressById(Integer id) throws RestaurantAddressNotFoundException;
    List<RestaurantAddressResponseDTO> getAllRestaurantAddresses();
}
