package com.br.SecurityJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.br.SecurityJWT.entities.RoleEntity;
import com.br.SecurityJWT.entities.UserEntity;
import com.br.SecurityJWT.enums.RoleName;
import com.br.SecurityJWT.repositories.RoleRepository;
import com.br.SecurityJWT.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Component
public class SetupRoles implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    boolean alreadySetup = false;

    @Transactional
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if(alreadySetup) return;

        for(RoleName roleName : RoleName.values()) {
            createRoleIfNotExists(roleName);
        }

        userRepository.save(
            UserEntity.builder()
                .id(1L)
                .firstname("id1")
                .lastname("role_manager")
                .email("user@email.com")
                .password("$2a$12$y102KHImXoS7Bxc6i0waI./3PJILzrs3H528fKqu1Hg00CQs.wLJi") //senha
                .role(roleRepository.findByName(RoleName.ROLE_USER).get())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build()
        );

        userRepository.save(
            UserEntity.builder()
                .id(2L)
                .firstname("id2")
                .lastname("role_user")
                .email("manager@email.com")
                .password("$2a$12$y102KHImXoS7Bxc6i0waI./3PJILzrs3H528fKqu1Hg00CQs.wLJi") //senha
                .role(roleRepository.findByName(RoleName.ROLE_MANAGER).get())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build()
        );

        userRepository.save(
            UserEntity.builder()
                .id(3L)
                .firstname("id3")
                .lastname("role_adimin")
                .email("admin@email.com")
                .password("$2a$12$y102KHImXoS7Bxc6i0waI./3PJILzrs3H528fKqu1Hg00CQs.wLJi") //senha
                .role(roleRepository.findByName(RoleName.ROLE_ADMIN).get())
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build()
        );

        alreadySetup = true;
        
    }
    
    private void createRoleIfNotExists(RoleName roleName) {
        if(!roleRepository.existsByName(roleName)) {
            RoleEntity role = RoleEntity.builder()
                .name(roleName)
                .build();
            roleRepository.save(role);
        }
    }
}
