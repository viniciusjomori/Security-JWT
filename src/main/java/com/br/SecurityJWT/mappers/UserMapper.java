package com.br.SecurityJWT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.br.SecurityJWT.DTO.UserResponseDTO;
import com.br.SecurityJWT.entities.RoleEntity;
import com.br.SecurityJWT.entities.UserEntity;
import com.br.SecurityJWT.enums.RoleName;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTACE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "role", target = "role", qualifiedByName = "roleEntityToName")
    UserResponseDTO toUserResponseDTO(UserEntity entity);

    Iterable<UserResponseDTO> allToUserResponseDTO(Iterable<UserEntity> entities);

    @Named("roleEntityToName")
    default RoleName roleEntityToName(RoleEntity entity) {
        return entity.getName();
    }
}
