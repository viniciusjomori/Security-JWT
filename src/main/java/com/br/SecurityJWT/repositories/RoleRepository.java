package com.br.SecurityJWT.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.br.SecurityJWT.entities.RoleEntity;
import com.br.SecurityJWT.enums.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {
    
    Optional<RoleEntity> findByName(RoleName name);
    boolean existsByName(RoleName name);

}