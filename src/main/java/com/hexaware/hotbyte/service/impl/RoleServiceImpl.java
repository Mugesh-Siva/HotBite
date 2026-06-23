package com.hexaware.hotbyte.service.impl;

import com.hexaware.hotbyte.dto.RoleRequestDTO;
import com.hexaware.hotbyte.dto.RoleResponseDTO;
import com.hexaware.hotbyte.entity.Role;
import com.hexaware.hotbyte.repository.RoleRepository;
import com.hexaware.hotbyte.service.RoleService;
import com.hexaware.hotbyte.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;

    @Override
    public RoleResponseDTO createRole(RoleRequestDTO dto) throws DuplicateResourceException, InvalidInputException {
        log.info("createRole called with dto: {}", dto);
        Role role = new Role();
        role.setRoleName(dto.getRoleName());

        Role savedRole = roleRepository.save(role);
        return mapToDTO(savedRole);
    }

    @Override
    public RoleResponseDTO updateRole(Integer id, RoleRequestDTO dto) throws RoleNotFoundException, InvalidInputException {
        log.info("updateRole called with id: {}, dto: {}", id, dto);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));

        role.setRoleName(dto.getRoleName());

        Role updatedRole = roleRepository.save(role);
        return mapToDTO(updatedRole);
    }

    @Override
    public void deleteRole(Integer id) throws RoleNotFoundException {
        log.info("deleteRole called with id: {}", id);
        if (!roleRepository.existsById(id)) {
            throw new RoleNotFoundException("Role not found with ID: " + id);
        }
        roleRepository.deleteById(id);
    }

    @Override
    public RoleResponseDTO getRoleById(Integer id) throws RoleNotFoundException {
        log.info("getRoleById called with id: {}", id);
        Role role = roleRepository.findById(id)
                .orElseThrow(() -> new RoleNotFoundException("Role not found with ID: " + id));
        return mapToDTO(role);
    }

    @Override
    public List<RoleResponseDTO> getAllRoles() {
        log.info("getAllRoles called");
        return roleRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private RoleResponseDTO mapToDTO(Role role) {
        RoleResponseDTO dto = new RoleResponseDTO();
        dto.setRoleId(role.getRoleId());
        dto.setRoleName(role.getRoleName());
        return dto;
    }
}
