package com.br.SecurityJWT.entities;

import java.util.Collection;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.br.SecurityJWT.enums.RoleName;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_role")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private RoleName name;
    
    @OneToMany(mappedBy = "role")
    private Collection<UserEntity> users;

    public SimpleGrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(this.name.toString());
    }
}
