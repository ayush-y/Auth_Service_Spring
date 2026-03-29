package org.example.authservice.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.authservice.services.JwtService;
import org.example.authservice.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;

    }

@Override
protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
        throws ServletException, IOException {

    System.out.println("=== FILTER START ===");

    String token = null;
    if (request.getCookies() != null) {
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("JwtToken")) {
                token = cookie.getValue();
            }
        }
    }

    try {

        if (token != null) {
            String email = jwtService.extractEmail(token);
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);
            boolean isValid = jwtService.validateToken(token, userDetails.getUsername());

            if (isValid) {
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

    } catch (Exception e) {
        System.out.println("EXCEPTION CAUGHT: " + e.getClass().getName());
        System.out.println("MESSAGE: " + e.getMessage());
        e.printStackTrace();
    }

    filterChain.doFilter(request, response);
}

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return uri.startsWith("/api/v1/auth/signing")  // ✅ skip filter for sign-in
                || uri.startsWith("/api/v1/auth/signup");   // ✅ skip filter for sign-up
    }
}
