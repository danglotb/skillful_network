package fr.uca.cdr.skillful_network.request;

import fr.uca.cdr.skillful_network.entities.user.User;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class JwtResponse {

    private User user;

    private String token;

    private Collection<? extends GrantedAuthority> authorities;

    public JwtResponse() {
    }

    public JwtResponse(User user, String token, Collection<? extends GrantedAuthority> authorities) {
        this.user = user;
        this.token = token;
        this.authorities = authorities;
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }
}
