package com.hms.configuration;

import com.hms.Service.JWTService;
import com.hms.entity.AppUser;
import com.hms.repository.AppUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.Optional;

@Component
public class JWTFilter extends OncePerRequestFilter {

   private AppUserRepository userRepository;
   private JWTService jwtService;

    public JWTFilter(AppUserRepository userRepository, JWTService jwtService) {
        this.userRepository = userRepository;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer")){
            String tokenVal = token.substring(8, token.length() - 1);
            String username = jwtService.getUsername(tokenVal);
            Optional<AppUser> byUsername = userRepository.findByUsername(username);
            if (byUsername.isPresent()){
                AppUser user = byUsername.get();

                UsernamePasswordAuthenticationToken auth = new
                        UsernamePasswordAuthenticationToken(user,null,
                        Collections.singleton(new SimpleGrantedAuthority(user.getRole())));

                auth.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        filterChain.doFilter(request,response);
    }
}
