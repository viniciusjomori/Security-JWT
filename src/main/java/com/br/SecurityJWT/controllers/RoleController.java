package com.br.SecurityJWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.SecurityJWT.DTO.RoleResponseDTO;
import com.br.SecurityJWT.entities.RoleEntity;
import com.br.SecurityJWT.enums.RoleName;
import com.br.SecurityJWT.mappers.RoleMapper;
import com.br.SecurityJWT.services.RoleService;

@RestController
@RequestMapping("role")
@CrossOrigin(origins = "*")
public class RoleController {
    
    @Autowired
    private RoleService service;

    @Autowired RoleMapper mapper;

    @GetMapping
    public ResponseEntity<Iterable<RoleResponseDTO>> findAll() {
        Iterable<RoleEntity> entities = service.findAll();
        return ResponseEntity.ok(mapper.allToRoleResponseDTO(entities));
    }

    @GetMapping("{name}")
    public ResponseEntity<RoleResponseDTO> findByName(@PathVariable(value = "name") String string) {
        RoleName roleName = service.toRoleName(string);
        RoleEntity entity = service.findByName(roleName);
        return ResponseEntity.ok(mapper.toRoleResponseDTO(entity));
    }

    @GetMapping("{name}/and-above")
    public ResponseEntity<Iterable<RoleResponseDTO>> findByNameAndAbove(@PathVariable(value = "name") String string) {
        RoleName roleName = service.toRoleName(string);
        Iterable<RoleEntity> entities = service.findByNameAndAbove(roleName);
        return ResponseEntity.ok(mapper.allToRoleResponseDTO(entities));
    }

    @GetMapping("{name}/and-below")
    public ResponseEntity<Iterable<RoleResponseDTO>> findByNameAndBelow(@PathVariable(value = "name") String string) {
        RoleName roleName = service.toRoleName(string);
        Iterable<RoleEntity> entities = service.findByNameAndBelow(roleName);
        return ResponseEntity.ok(mapper.allToRoleResponseDTO(entities));
    }
}
