package com.example.showcase.security.jwt;

import com.example.showcase.service.AuthUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final AuthUserDetailsService userDetailsService;
    private final JwtPublicPathsConfig publicPathsConfig;

    // Вызывается для всех защищенных эндпоинтов после shouldNotFilter()
    @Override
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {

        String jwt = parseJwtFromRequest(request);

        if (jwt != null && jwtUtils.isTokenValid(jwt)) {

            String username = jwtUtils.extractUsername(jwt);
            SecurityContext securityContext = SecurityContextHolder.getContext();

            if (username != null && securityContext.getAuthentication() == null) {

                UserDetails userDetails = userDetailsService.loadUserByUsername(username);

                if (jwtUtils.validateAccessToken(jwt, userDetails)) {

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities()
                            );

                    authentication.setDetails(
                            new WebAuthenticationDetailsSource().buildDetails(request)
                    );

                    securityContext.setAuthentication(authentication);

                    log.debug("Успешная аутентификация пользователя {}", username);
                }
            }
        }

        filterChain.doFilter(request, response);
    }

    private String parseJwtFromRequest(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");

        if (headerAuth != null && headerAuth.startsWith("Bearer "))
            return headerAuth.substring(7);

        return null;
    }

    // вызывается сразу после поступления http запроса
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        boolean isPublic = publicPathsConfig.isPublic(path);

        if (isPublic)
            log.trace("Skipping JWT filter for public path: {}", path);

        return isPublic;
    }
}
