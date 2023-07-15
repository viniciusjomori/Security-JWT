package com.br.SecurityJWT.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.SecurityJWT.entities.RoleEntity;
import com.br.SecurityJWT.enums.RoleName;
import com.br.SecurityJWT.repositories.RoleRepository;

@Service
public class RoleService {
    
    @Autowired
    private RoleRepository repository;

    @Autowired
    private RoleHierarchy roleHierarchy;

    public RoleName toRoleName(String string) {
        if(string == null) return null;
        string = string.toUpperCase();
        if(!string.startsWith("ROLE_")) string = "ROLE_"+string;
        try {
            return RoleName.valueOf(string);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            );
        }
    }

    public Iterable<RoleEntity> findAll() {
        return repository.findAll();
    }

    public RoleEntity findByName(RoleName name) {
        return repository.findByName(name)
            .orElseThrow();
    }

    public boolean isAbove(SimpleGrantedAuthority it, SimpleGrantedAuthority that) {
        return roleHierarchy.getReachableGrantedAuthorities(
            Collections.singleton(it))
            .contains(that);
    }

    public Iterable<RoleEntity> findByNameAndAbove(RoleName name) {
        RoleEntity it = findByName(name);
        Collection<RoleEntity> finded = new ArrayList<>();
        for (RoleEntity that : findAll()) {
            if(isAbove(that.getAuthority(), it.getAuthority()))
                finded.add(that);
        }
        return finded;
    }

    public Iterable<RoleEntity> findByNameAndBelow(RoleName name) {
        RoleEntity it = findByName(name);
        Collection<RoleEntity> finded = new ArrayList<>();
        for (RoleEntity that : findAll()) {
            if(isAbove(it.getAuthority(), that.getAuthority()))
                finded.add(that);
        }
        return finded;
    }

}
