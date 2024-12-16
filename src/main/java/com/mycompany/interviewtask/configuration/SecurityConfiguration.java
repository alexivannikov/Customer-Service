package com.mycompany.interviewtask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;

import static com.mycompany.interviewtask.enumeration.UserRole.ADMIN;
import static com.mycompany.interviewtask.enumeration.UserRole.USER;

/**
 * Конфигурация защищенного доступа к методам API
 */
@Component
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public UserDetailsService users() {
        var passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        var user = User.withUsername("user")
                .password(passwordEncoder.encode("user"))
                .roles(USER.name())
                .build();
        var admin = User.withUsername("admin")
                .password(passwordEncoder.encode("admin"))
                .roles(ADMIN.name())
                .build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeRequests()
                .antMatchers("/customers/**").hasAnyAuthority(ADMIN.getCode())
                .and().httpBasic()
                .and().csrf().disable()
                .formLogin().disable()
                .build();
    }
}
