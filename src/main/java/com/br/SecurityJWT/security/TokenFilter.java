package com.br.SecurityJWT.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.br.SecurityJWT.entities.UserEntity;
import com.br.SecurityJWT.repositories.TokenRepository;
import com.br.SecurityJWT.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class TokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private TokenRepository tokenRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal
    (
        HttpServletRequest request, 
        HttpServletResponse response, 
        FilterChain filterChain
    ) 
        throws ServletException, IOException
    {

        String authHeader = request.getHeader("Authorization");

        if(jwtUtil.isTokenValid(authHeader)) {

            String token = authHeader.substring(7);

            boolean isTokenValid = tokenRepository.findByToken(token)
                .map(t -> !t.isExpired() && !t.isRevoked())
                .orElse(false);

            if(isTokenValid) {

                String email = jwtUtil.extractSubject(token);
                UserEntity user = userRepository.findByEmail(email)
                    .orElseThrow();
                
                if(user != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.getAuthorities()
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }

        filterChain.doFilter(request, response);
        
    }
    
}
