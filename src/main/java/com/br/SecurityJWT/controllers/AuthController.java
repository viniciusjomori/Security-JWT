package com.br.SecurityJWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.SecurityJWT.DTO.AuthResponseDTO;
import com.br.SecurityJWT.DTO.LoginRequestDTO;
import com.br.SecurityJWT.services.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
@CrossOrigin(origins = "*")
public class AuthController {
    
    @Autowired
    private AuthService service;

    @PostMapping
    public ResponseEntity<AuthResponseDTO> authenticate(@RequestBody @Valid LoginRequestDTO login) {
        return ResponseEntity.ok(service.authenticate(login));
    }

}
