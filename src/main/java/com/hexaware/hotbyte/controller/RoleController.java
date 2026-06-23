package com.hexaware.hotbyte.controller;

import com.hexaware.hotbyte.dto.RoleRequestDTO;
import com.hexaware.hotbyte.dto.RoleResponseDTO;
import com.hexaware.hotbyte.service.RoleService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/roles")
public class RoleController {

    @Autowired
    RoleService roleService;

    @PostMapping("/add")
    public ResponseEntity<RoleResponseDTO> createRole(@Valid @RequestBody RoleRequestDTO dto) {
        log.info("createRole endpoint called");
        RoleResponseDTO response = roleService.createRole(dto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<RoleResponseDTO> updateRole(@PathVariable Integer id, @Valid @RequestBody RoleRequestDTO dto) {
        log.info("updateRole endpoint called with id: {}", id);
        RoleResponseDTO response = roleService.updateRole(id, dto);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<RoleResponseDTO> getRoleById(@PathVariable Integer id) {
        log.info("getRoleById endpoint called with id: {}", id);
        RoleResponseDTO response = roleService.getRoleById(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<RoleResponseDTO>> getAllRoles() {
        log.info("getAllRoles endpoint called");
        List<RoleResponseDTO> response = roleService.getAllRoles();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/deletebyid/{id}")
    public ResponseEntity<String> deleteRoleById(@PathVariable Integer id) {
        log.info("deleteRoleById endpoint called with id: {}", id);
        roleService.deleteRole(id);
        return new ResponseEntity<>("Role deleted successfully", HttpStatus.ACCEPTED);
    }
}
