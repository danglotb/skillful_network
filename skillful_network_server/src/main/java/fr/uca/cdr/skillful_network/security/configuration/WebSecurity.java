package fr.uca.cdr.skillful_network.security.configuration;

import fr.uca.cdr.skillful_network.security.filter.JWTAuthorizationFilter;
import fr.uca.cdr.skillful_network.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.context.annotation.Bean;

import static fr.uca.cdr.skillful_network.security.SecurityConstants.LOG_IN_URL;
import static fr.uca.cdr.skillful_network.security.SecurityConstants.REGISTER_URL;

@Profile({"dev", "prod"})
@EnableWebSecurity
public class WebSecurity extends AbstractConfiguration {

    // ###########################################################################
    // WebSecurityConfigurerAdapter boolean for HTTP Pattern Matcher Security toggle
    // to me be set in application.properties file :
    // - security enabled (or empty/null/commented, default then):
    // api.security.httpPatternMatcher.disabled=false
    // - security disabled :
    // api.security.httpPatternMatcher.disabled=true
    // ###########################################################################

    @Value("${api.security.httpPatternMatcher.disabled:false}")
    private boolean httpPatternMatcherDisabled;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests().antMatchers(HttpMethod.OPTIONS, "/**").permitAll();
        if ( ! httpPatternMatcherDisabled) { // http pattern matcher enabled
            http.authorizeRequests()
                .antMatchers(HttpMethod.POST, REGISTER_URL, LOG_IN_URL, "/h2/**", "/whoami").permitAll()
                .antMatchers(HttpMethod.GET, "/favicon.ico",
                        "/v2/api-docs",
                        "/configuration/ui",
                        "/swagger-resources/**",
                        "/configuration/security",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/h2/**").permitAll().anyRequest().authenticated();
        } else { // http pattern matcher disabled
            http.authorizeRequests()
                    .anyRequest().permitAll(); // toutes les pages/requêtes sont accessibles
        }

        http.authorizeRequests()
                .and()
                .addFilter(new JWTAuthorizationFilter(authenticationManager()))
                // this disables session creation on Spring Security
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().headers().frameOptions().disable();
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(s -> this.userService.getByEmail(s)).passwordEncoder(this.bCryptPasswordEncoder);
    }

}