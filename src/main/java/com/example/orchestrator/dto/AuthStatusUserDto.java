package com.example.orchestrator.dto;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import java.util.Collection;

@Data
public class AuthStatusUserDto {
    private Long id;
    private String username;
    private Collection<? extends GrantedAuthority> authorities;
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    public AuthStatusUserDto(Long id, String username, Collection<? extends GrantedAuthority> authorities,
                   boolean accountNonExpired, boolean accountNonLocked,
                   boolean credentialsNonExpired, boolean enabled) {
        this.id = id;
        this.username = username;
        this.authorities = authorities;
        this.accountNonExpired = accountNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled = enabled;
    }
}
