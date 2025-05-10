package com.example.fitness_app_backend.config;

import com.example.fitness_app_backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final HandlerExceptionResolver handlerExceptionResolver;

    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
            final String authHeader = request.getHeader("Authorization");
            logger.info("Incoming request: header -- {}, method -- {}", request.getHeader("Authorization"), request.getMethod());

            if(authHeader == null || !authHeader.startsWith("Bearer ")) {
                logger.info("No Authorization header found, proceeding without authentication.");
                filterChain.doFilter(request, response);
                return;
            }

            try{
                final String jwt = authHeader.substring(7);
                final String userEmail = jwtService.extractUsername(jwt);

                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

                if(userEmail != null && authentication == null){
                    UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

                    if(jwtService.isTokenValid(jwt, userDetails)){
                        logger.debug("JWT is valid, setting authentication");
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );

                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }else{
                        logger.warn("JWT is invalid");
                    }
                }else{
                    logger.warn("User email or authentication is null, skipping token validation");
                }
                filterChain.doFilter(request,response);
            } catch (Exception exception){
                logger.error("An error occurred during JWT authentication", exception);
                handlerExceptionResolver.resolveException(request,response,null,exception);
            }

    }

}
