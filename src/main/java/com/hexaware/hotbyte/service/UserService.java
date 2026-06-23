package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.UserRequestDTO;
import com.hexaware.hotbyte.dto.UserResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface UserService {
    UserResponseDTO createUser(UserRequestDTO userDto) throws DuplicateResourceException, InvalidInputException;
    UserResponseDTO updateUser(Integer id, UserRequestDTO userDto) throws UserNotFoundException, InvalidInputException;
    void deleteUser(Integer id) throws UserNotFoundException;
    UserResponseDTO getUserById(Integer id) throws UserNotFoundException;
    List<UserResponseDTO> getAllUsers();
}
