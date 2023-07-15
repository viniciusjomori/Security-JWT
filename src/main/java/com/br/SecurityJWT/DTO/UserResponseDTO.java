package com.br.SecurityJWT.DTO;

import com.br.SecurityJWT.enums.RoleName;

public record UserResponseDTO(
    Long id,
    String firstname,
    String lastname,
    String email,
    RoleName role
) {}
