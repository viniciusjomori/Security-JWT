package com.br.SecurityJWT.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.br.SecurityJWT.DTO.AuthResponseDTO;
import com.br.SecurityJWT.DTO.LoginRequestDTO;
import com.br.SecurityJWT.entities.TokenEntity;
import com.br.SecurityJWT.entities.UserEntity;
import com.br.SecurityJWT.repositories.TokenRepository;
import com.br.SecurityJWT.repositories.UserRepository;
import com.br.SecurityJWT.security.JwtUtil;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private AuthenticationManager authManager;
    
    public AuthResponseDTO authenticate(LoginRequestDTO login) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            login.email(),
            login.password()
        );
        authManager.authenticate(authenticationToken);
        UserEntity user = userRepository.findByEmail(login.email())
            .get();
        return createNewAuthResponse(user);
    }

    public AuthResponseDTO createNewAuthResponse(UserEntity user) {
        revokeAllUserTokens(user);
        String accessToken = jwtUtil.generateToken(user);
        saveUserToken(user, accessToken);
        return new AuthResponseDTO(
            accessToken
        );
    }

    public void saveUserToken(UserEntity user, String token) {
        TokenEntity tokenEntity = TokenEntity.builder()
            .user(user)
            .token(token)
            .expired(false)
            .revoked(false)
            .build();
        tokenRepository.save(tokenEntity);
    }

    public void revokeAllUserTokens(UserEntity user) {
        Iterable<TokenEntity> validTokens = tokenRepository.findAllValidTokens(user.getId());
        validTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validTokens);
    }
}
