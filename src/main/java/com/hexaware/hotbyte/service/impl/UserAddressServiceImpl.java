package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.UserAddressRequestDTO;
import com.hexaware.hotbyte.dto.UserAddressResponseDTO;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.entity.UserAddress;
import com.hexaware.hotbyte.repository.UserAddressRepository;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.service.UserAddressService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserAddressServiceImpl implements UserAddressService {

    @Autowired
    UserAddressRepository userAddressRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public UserAddressResponseDTO createUserAddress(UserAddressRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createUserAddress called with dto: {}", dto);
        UserAddress address = new UserAddress();
        address.setAddressLine1(dto.getAddressLine1());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setIsDefault(dto.getIsDefault());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getUserId()));
        address.setUser(user);

        UserAddress savedAddress = userAddressRepository.save(address);
        return mapToDTO(savedAddress);
    }

    @Override
    public UserAddressResponseDTO updateUserAddress(Integer id, UserAddressRequestDTO dto) throws UserAddressNotFoundException, InvalidInputException {
        log.info("updateUserAddress called with id: {}, dto: {}", id, dto);
        UserAddress address = userAddressRepository.findById(id)
                .orElseThrow(() -> new UserAddressNotFoundException("UserAddress not found with ID: " + id));

        address.setAddressLine1(dto.getAddressLine1());
        address.setCity(dto.getCity());
        address.setState(dto.getState());
        address.setZipCode(dto.getZipCode());
        address.setIsDefault(dto.getIsDefault());

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + dto.getUserId()));
        address.setUser(user);

        UserAddress updatedAddress = userAddressRepository.save(address);
        return mapToDTO(updatedAddress);
    }

    @Override
    public void deleteUserAddress(Integer id) throws UserAddressNotFoundException {
        log.info("deleteUserAddress called with id: {}", id);
        if (!userAddressRepository.existsById(id)) {
            throw new UserAddressNotFoundException("UserAddress not found with ID: " + id);
        }
        userAddressRepository.deleteById(id);
    }

    @Override
    public UserAddressResponseDTO getUserAddressById(Integer id) throws UserAddressNotFoundException {
        log.info("getUserAddressById called with id: {}", id);
        UserAddress address = userAddressRepository.findById(id)
                .orElseThrow(() -> new UserAddressNotFoundException("UserAddress not found with ID: " + id));
        return mapToDTO(address);
    }

    @Override
    public List<UserAddressResponseDTO> getAllUserAddresses() {
        log.info("getAllUserAddresses called");
        return userAddressRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserAddressResponseDTO mapToDTO(UserAddress address) {
        UserAddressResponseDTO dto = new UserAddressResponseDTO();
        dto.setAddressId(address.getAddressId());
        dto.setAddressLine1(address.getAddressLine1());
        dto.setCity(address.getCity());
        dto.setState(address.getState());
        dto.setZipCode(address.getZipCode());
        dto.setIsDefault(address.getIsDefault());
        if(address.getUser() != null) dto.setUserId(address.getUser().getUserId());
        return dto;
    }
}
