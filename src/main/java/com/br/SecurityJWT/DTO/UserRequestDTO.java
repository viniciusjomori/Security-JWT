package com.br.SecurityJWT.DTO;

import com.br.SecurityJWT.enums.RoleName;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRequestDTO(
    @NotBlank 
    String firstname,

    @NotBlank
    String lastname,

    @NotBlank
    @Email 
    String email,

    @NotBlank 
    String password,

    @NotNull
    RoleName role
) {}
