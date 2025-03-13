package com.hms.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
public class SecurityConfig {

    private JWTFilter jwtFilter;

    public SecurityConfig(JWTFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception
    {
       http.cors().disable().csrf().disable();

       http.addFilterBefore(jwtFilter, AuthorizationFilter.class);
       http.authorizeHttpRequests().anyRequest().permitAll();
//       http.authorizeHttpRequests()
//               .requestMatchers("/api/v1/hms/user/login","/api/v1/hms/user/signup",
//                       "/api/v1/hms/user/property-owner-signup")
//               .permitAll()
//               .requestMatchers("/api/v1/country/add-country")
//               .hasAnyRole("OWNER","ADMIN")
//               .anyRequest()
//               .authenticated();

       return http.build();
    }

}
