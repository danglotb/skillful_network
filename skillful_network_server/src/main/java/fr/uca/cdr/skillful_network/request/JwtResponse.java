package fr.uca.cdr.skillful_network.request;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {

    public final String token;

    public final Collection<? extends GrantedAuthority> authorities;

    public JwtResponse(String token,  Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.authorities = authorities;
    }

}
