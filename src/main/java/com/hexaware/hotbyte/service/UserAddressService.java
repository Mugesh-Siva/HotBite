package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.UserAddressRequestDTO;
import com.hexaware.hotbyte.dto.UserAddressResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface UserAddressService {
    UserAddressResponseDTO createUserAddress(UserAddressRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    UserAddressResponseDTO updateUserAddress(Integer id, UserAddressRequestDTO dto) throws UserAddressNotFoundException, InvalidInputException;
    void deleteUserAddress(Integer id) throws UserAddressNotFoundException;
    UserAddressResponseDTO getUserAddressById(Integer id) throws UserAddressNotFoundException;
    List<UserAddressResponseDTO> getAllUserAddresses();
}
