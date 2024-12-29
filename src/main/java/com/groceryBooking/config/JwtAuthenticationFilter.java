package com.groceryBooking.config;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String token = extractJwtFromRequest(request);

        if (token != null && jwtUtil.validateToken(token)) {
            String username = jwtUtil.extractClaims(token).getSubject();
            Claims claims = jwtUtil.extractClaims(token);
            String role = claims.get("role").toString();

//            System.out.println("Role: " + role);

            List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role)); // Add ROLE_ prefix
//            System.out.println("Authorities: " + authorities);
            // Create authentication object
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
//        if (SecurityContextHolder.getContext().getAuthentication() == null) {
//            System.out.println("Authentication is null");
//        } else {
//            System.out.println("Authentication is valid");
//            System.out.println(SecurityContextHolder.getContext().getAuthentication().getAuthorities());
//        }
        filterChain.doFilter(request, response);
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7); // Remove the "Bearer " prefix
        }
        return null;
    }
}
