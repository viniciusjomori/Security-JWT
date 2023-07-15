package com.br.SecurityJWT.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.br.SecurityJWT.DTO.RoleResponseDTO;
import com.br.SecurityJWT.DTO.UserResponseDTO;
import com.br.SecurityJWT.entities.RoleEntity;
import com.br.SecurityJWT.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleMapper INSTACE = Mappers.getMapper(RoleMapper.class);

    @Mapping(source = "users", target = "users", qualifiedByName = "userEntityToDTO")
    RoleResponseDTO toRoleResponseDTO(RoleEntity entity);

    Iterable<RoleResponseDTO> allToRoleResponseDTO(Iterable<RoleEntity> entities);

    @Named("userEntityToDTO")
    default Iterable<UserResponseDTO> userEntityToDTO(Iterable<UserEntity> users) {
        return Mappers.getMapper(UserMapper.class).allToUserResponseDTO(users);
    }
}
