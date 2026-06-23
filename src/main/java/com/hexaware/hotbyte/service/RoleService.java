package com.hexaware.hotbyte.service;

import com.hexaware.hotbyte.dto.RoleRequestDTO;
import com.hexaware.hotbyte.dto.RoleResponseDTO;
import java.util.List;
import com.hexaware.hotbyte.exception.*;

public interface RoleService {
    RoleResponseDTO createRole(RoleRequestDTO dto) throws DuplicateResourceException, InvalidInputException;
    RoleResponseDTO updateRole(Integer id, RoleRequestDTO dto) throws RoleNotFoundException, InvalidInputException;
    void deleteRole(Integer id) throws RoleNotFoundException;
    RoleResponseDTO getRoleById(Integer id) throws RoleNotFoundException;
    List<RoleResponseDTO> getAllRoles();
}
