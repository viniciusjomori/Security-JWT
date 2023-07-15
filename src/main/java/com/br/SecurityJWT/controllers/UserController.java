package com.br.SecurityJWT.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.br.SecurityJWT.DTO.AuthResponseDTO;
import com.br.SecurityJWT.DTO.ResponseMessage;
import com.br.SecurityJWT.DTO.UserRequestDTO;
import com.br.SecurityJWT.DTO.UserResponseDTO;
import com.br.SecurityJWT.entities.UserEntity;
import com.br.SecurityJWT.mappers.UserMapper;
import com.br.SecurityJWT.services.AuthService;
import com.br.SecurityJWT.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private UserMapper mapper;
    
    @GetMapping
    public ResponseEntity<Iterable<UserResponseDTO>> findAll() {
        Iterable<UserEntity> entities = userService.findAll();
        return ResponseEntity.ok(mapper.allToUserResponseDTO(entities));
    }

    @GetMapping("authenticated")
    public ResponseEntity<UserResponseDTO> findAuthUser() {
        UserEntity entity = userService.findAuthUser();
        return ResponseEntity.ok(mapper.toUserResponseDTO(entity));
    }

    @PostMapping
    public ResponseEntity<AuthResponseDTO> createUser(@RequestBody @Valid UserRequestDTO requestDTO) {
        UserEntity entity = userService.createUser(requestDTO);
        return ResponseEntity.ok(authService.createNewAuthResponse(entity));
    }

    @PutMapping("{id}")
    public ResponseEntity<AuthResponseDTO> updateUser(@RequestBody @Valid UserRequestDTO requestDTO, @PathVariable long id) {
        UserEntity entity = userService.updateUser(requestDTO, id);
        return ResponseEntity.ok(authService.createNewAuthResponse(entity));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<ResponseMessage> deleteUser(@PathVariable long id) {
        userService.deleteById(id);
        return ResponseEntity.ok(new ResponseMessage(
            "successfully deleted",
            HttpStatus.OK
        ));
    }

    @PostMapping("logout")
    public ResponseEntity<ResponseMessage> logout() {
        UserEntity user = userService.findAuthUser();
        authService.revokeAllUserTokens(user);
        return ResponseEntity.ok(new ResponseMessage(
            "logout",
            HttpStatus.OK
        ));
    }
}
