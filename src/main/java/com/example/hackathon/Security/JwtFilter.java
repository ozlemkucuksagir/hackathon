package com.example.hackathon.Security;

import java.io.IOException;


import com.example.hackathon.Security.Config.WebSecurityConfig;
import com.example.hackathon.Service.TokenService;
import com.example.hackathon.Service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserService userDetailsService;
    @Autowired
    private TokenManager tokenManager;
    @Autowired

    private WebSecurityConfig webSecurityConfig;
    @Autowired
    private TokenService tokenService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String requestUrl = request.getRequestURI();
        // System.out.println("girmedi");
        // Eğer URL "/auth/login" ise, filtreyi çalıştırma (true döndürme)
        return requestUrl.equals("/auth/login");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // İstek yapılan URL'yi alın
        String requestUrl = request.getRequestURI();

        // Eğer URL "/login" ise, filtrenin çalışmasını engelle
       /* if (requestUrl.equals("/login")) {
            filterChain.doFilter(request, response);
            return; // Filtreyi sonlandır
        }*/

        String tokenHeader = request.getHeader("Authorization");
        String username = null;
        String token = null;
        if (tokenHeader != null && tokenHeader.startsWith("Bearer ")) {
            token = tokenHeader.substring(7);
            try {
                if (tokenService.isInvalidateToken(token)) {//todo
                    username = tokenManager.getUsernameFromToken(token);
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Unable to get JWT Token");//&&

            } catch (ExpiredJwtException e) {
                System.out.println("JWT Token has expired");
            }
        } else {
            //  String a = webSecurityConfig.passwordEncoder().encode("deneme");
            //  System.out.println(a);
            System.out.println("Bearer String not found in token");
        }
        if (null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (tokenManager.validateJwtToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken
                        authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null,
                        userDetails.getAuthorities());
                authenticationToken.setDetails(new
                        WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}