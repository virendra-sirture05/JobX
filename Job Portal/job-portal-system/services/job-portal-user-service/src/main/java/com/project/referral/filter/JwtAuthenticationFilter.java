package com.project.referral.filter;

import com.project.referral.auth.service.CustomUserDetailsService;
import com.project.referral.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter
        extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(

            HttpServletRequest request,

            HttpServletResponse response,

            FilterChain filterChain

    ) throws ServletException, IOException {

        Cookie[] cookies = request.getCookies();

        if (cookies == null) {

            filterChain.doFilter(request, response);

            return;

        }

        String jwt = null;

        for (Cookie cookie : cookies) {

            if ("accessToken".equals(cookie.getName())) {

                jwt = cookie.getValue();

                break;

            }

        }

        if (jwt == null) {

            filterChain.doFilter(request, response);

            return;

        }

        try {

            String email =
                    jwtService.extractEmail(jwt);

            if (SecurityContextHolder.getContext()
                    .getAuthentication() == null) {

                UserDetails userDetails =
                        userDetailsService
                                .loadUserByUsername(email);

                if (jwtService.isTokenValid(
                        jwt,
                        userDetails.getUsername())) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(

                                    userDetails,

                                    null,

                                    userDetails.getAuthorities()

                            );

                    authentication.setDetails(

                            new WebAuthenticationDetailsSource()
                                    .buildDetails(request)

                    );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                }

            }

        } catch (Exception ignored) {

        }

        filterChain.doFilter(request, response);

    }

}