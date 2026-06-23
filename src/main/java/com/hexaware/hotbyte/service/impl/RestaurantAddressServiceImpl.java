package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.RestaurantAddressRequestDTO;
import com.hexaware.hotbyte.dto.RestaurantAddressResponseDTO;
import com.hexaware.hotbyte.entity.Restaurant;
import com.hexaware.hotbyte.entity.RestaurantAddress;
import com.hexaware.hotbyte.repository.RestaurantAddressRepository;
import com.hexaware.hotbyte.repository.RestaurantRepository;
import com.hexaware.hotbyte.service.RestaurantAddressService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RestaurantAddressServiceImpl implements RestaurantAddressService {

    @Autowired
    RestaurantAddressRepository restaurantAddressRepository;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public RestaurantAddressResponseDTO createRestaurantAddress(RestaurantAddressRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createRestaurantAddress called with dto: {}", dto);
        RestaurantAddress address = new RestaurantAddress();
        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));
        address.setRestaurant(restaurant);

        RestaurantAddress savedAddress = restaurantAddressRepository.save(address);
        return mapToDTO(savedAddress);
    }

    @Override
    public RestaurantAddressResponseDTO updateRestaurantAddress(Integer id, RestaurantAddressRequestDTO dto) throws RestaurantAddressNotFoundException, InvalidInputException {
        log.info("updateRestaurantAddress called with id: {}, dto: {}", id, dto);
        RestaurantAddress address = restaurantAddressRepository.findById(id)
                .orElseThrow(() -> new RestaurantAddressNotFoundException("RestaurantAddress not found with ID: " + id));

        address.setAddressLine1(dto.getAddressLine1());
        address.setAddressLine2(dto.getAddressLine2());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());

        Restaurant restaurant = restaurantRepository.findById(dto.getRestaurantId())
                .orElseThrow(() -> new RestaurantNotFoundException("Restaurant not found with ID: " + dto.getRestaurantId()));
        address.setRestaurant(restaurant);

        RestaurantAddress updatedAddress = restaurantAddressRepository.save(address);
        return mapToDTO(updatedAddress);
    }

    @Override
    public void deleteRestaurantAddress(Integer id) throws RestaurantAddressNotFoundException {
        log.info("deleteRestaurantAddress called with id: {}", id);
        if (!restaurantAddressRepository.existsById(id)) {
            throw new RestaurantAddressNotFoundException("RestaurantAddress not found with ID: " + id);
        }
        restaurantAddressRepository.deleteById(id);
    }

    @Override
    public RestaurantAddressResponseDTO getRestaurantAddressById(Integer id) throws RestaurantAddressNotFoundException {
        log.info("getRestaurantAddressById called with id: {}", id);
        RestaurantAddress address = restaurantAddressRepository.findById(id)
                .orElseThrow(() -> new RestaurantAddressNotFoundException("RestaurantAddress not found with ID: " + id));
        return mapToDTO(address);
    }

    @Override
    public List<RestaurantAddressResponseDTO> getAllRestaurantAddresses() {
        log.info("getAllRestaurantAddresses called");
        return restaurantAddressRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RestaurantAddressResponseDTO mapToDTO(RestaurantAddress address) {
        RestaurantAddressResponseDTO dto = new RestaurantAddressResponseDTO();
        dto.setRestaurantAddressId(address.getRestaurantAddressId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setAddressLine2(address.getAddressLine2());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        if(address.getRestaurant() != null) dto.setRestaurantId(address.getRestaurant().getRestaurantId());
        return dto;
    }
}
