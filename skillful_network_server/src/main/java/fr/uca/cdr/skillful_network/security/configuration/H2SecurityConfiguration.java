package fr.uca.cdr.skillful_network.security.configuration;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.header.writers.frameoptions.WhiteListedAllowFromStrategy;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;

import java.util.Arrays;
import java.util.Collections;

@Profile({"test"})
@ConditionalOnProperty(
        value = "spring.h2.console.enabled",
        havingValue = "true",
        matchIfMissing = false)
@Configuration
public class H2SecurityConfiguration extends AbstractConfiguration {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().sameOrigin();
        http.csrf().ignoringAntMatchers("/h2/**");
        http.authorizeRequests().antMatchers("/h2/**").permitAll();
        http.cors().disable();
    }
}