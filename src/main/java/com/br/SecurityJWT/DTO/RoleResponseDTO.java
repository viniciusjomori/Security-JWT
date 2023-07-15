package com.br.SecurityJWT.DTO;

import com.br.SecurityJWT.enums.RoleName;

public record RoleResponseDTO(
    RoleName name,
    Iterable<UserResponseDTO> users
) {}
