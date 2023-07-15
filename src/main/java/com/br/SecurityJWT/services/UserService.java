package com.br.SecurityJWT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.br.SecurityJWT.DTO.UserRequestDTO;
import com.br.SecurityJWT.entities.UserEntity;
import com.br.SecurityJWT.repositories.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoleService roleService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Iterable<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findAuthUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(principal instanceof UserEntity) {
            return (UserEntity) principal;
        } else {
            return null;
        }
    }

    public UserEntity createUser(UserRequestDTO requestDTO) {
        return userRepository.save(
            UserEntity.builder()
                .firstname(requestDTO.firstname())
                .lastname(requestDTO.lastname())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .role(roleService.findByName(requestDTO.role()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .credentialsNonExpired(true)
                .enabled(true)
                .build()
        );
    }

    public UserEntity updateUser(UserRequestDTO requestDTO, Long id) {
        UserEntity user = findById(id);
        return userRepository.save(
            UserEntity.builder()
                .id(id)
                .firstname(requestDTO.firstname())
                .lastname(requestDTO.lastname())
                .email(requestDTO.email())
                .password(passwordEncoder.encode(requestDTO.password()))
                .role(roleService.findByName(requestDTO.role()))
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonExpired())
                .credentialsNonExpired(user.isAccountNonExpired())
                .enabled(user.isAccountNonExpired())
                .build()
        );
    }

    public void deleteById(long id) {
        if(userRepository.existsById(id))
            userRepository.deleteById(id);
        else {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            );
        }
             
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));
    }

}
