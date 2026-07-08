package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.UserRequestDTO;
import com.hexaware.hotbyte.dto.UserResponseDTO;
import com.hexaware.hotbyte.entity.Role;
import com.hexaware.hotbyte.entity.User;
import com.hexaware.hotbyte.repository.RoleRepository;
import com.hexaware.hotbyte.repository.UserRepository;
import com.hexaware.hotbyte.service.UserService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;
    
    @Autowired
    com.hexaware.hotbyte.service.EmailService emailService;

    @Autowired
    org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;

    @Override
    public UserResponseDTO createUser(UserRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createUser called with dto: {}", dto);
        
        if (userRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("User with email " + dto.getEmail() + " already exists.");
        }

        User user = new User();
        user.setFullName(dto.getFullName());
        user.setEmail(dto.getEmail());
        user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        user.setContactNumber(dto.getContactNumber());
        user.setGender(dto.getGender());

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + dto.getRoleId()));
        user.setRole(role);

        User savedUser = userRepository.save(user);
        return mapToDTO(savedUser);
    }

    @Override
    public UserResponseDTO updateUser(Integer id, UserRequestDTO dto) throws UserNotFoundException, InvalidInputException {
        log.info("updateUser called with id: {}, dto: {}", id, dto);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));

        user.setFullName(dto.getFullName());
        user.setContactNumber(dto.getContactNumber());
        user.setGender(dto.getGender());

        if (dto.getPassword() != null && !dto.getPassword().isEmpty()) {
            user.setPasswordHash(passwordEncoder.encode(dto.getPassword()));
        }

        Role role = roleRepository.findById(dto.getRoleId())
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + dto.getRoleId()));
        user.setRole(role);

        User updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }

    @Override
    public void deleteUser(Integer id) throws UserNotFoundException {
        log.info("deleteUser called with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new UserNotFoundException("User not found with ID: " + id);
        }
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDTO getUserById(Integer id) throws UserNotFoundException {
        log.info("getUserById called with id: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + id));
        return mapToDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        log.info("getAllUsers called");
        return userRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private UserResponseDTO mapToDTO(User user) {
        UserResponseDTO dto = new UserResponseDTO();
        dto.setUserId(user.getUserId());
        dto.setFullName(user.getFullName());
        dto.setEmail(user.getEmail());
        dto.setContactNumber(user.getContactNumber());
        dto.setGender(user.getGender());
        dto.setCreatedAt(user.getCreatedAt());
        if (user.getRole() != null) {
            dto.setRoleId(user.getRole().getRoleId());
        }
        return dto;
    }

    @Override
    public void generateOtpAndSendEmail(String email) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        String otp = String.format("%06d", new java.util.Random().nextInt(999999));
        user.setResetOtp(otp);
        user.setResetOtpExpiry(java.time.LocalDateTime.now().plusMinutes(10));
        userRepository.save(user);

        String emailBody = "Hot Bite Online Food Ordering and Delivery App\n Your OTP for password reset is: " + otp + "\nThis OTP is valid for 10 minutes.";
        emailService.sendEmail(user.getEmail(), "Password Reset OTP", emailBody);
        log.info("OTP sent to email: {}", email);
    }

    @Override
    public boolean verifyOtp(String email, String otp) throws UserNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        if (user.getResetOtp() != null && user.getResetOtp().equals(otp)) {
            if (user.getResetOtpExpiry() != null && user.getResetOtpExpiry().isAfter(java.time.LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void resetPassword(String email, String otp, String newPassword) throws UserNotFoundException, InvalidInputException {
        if (!verifyOtp(email, otp)) {
            throw new InvalidInputException("Invalid or expired OTP");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found with email: " + email));

        user.setPasswordHash(passwordEncoder.encode(newPassword));
        user.setResetOtp(null);
        user.setResetOtpExpiry(null);
        userRepository.save(user);
        log.info("Password reset successful for email: {}", email);
    }
}
