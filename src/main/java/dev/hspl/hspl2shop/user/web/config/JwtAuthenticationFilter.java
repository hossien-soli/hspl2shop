package dev.hspl.hspl2shop.user.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@NullMarked
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final AuthenticationManager authManager;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String headerValue = request.getHeader("Authorization");
        boolean validate = headerValue != null && headerValue.startsWith("Bearer ")
                && headerValue.length() >= 15;

        if (validate) {
            String jwtAccessToken = headerValue.substring(7);
            try {
                var authResult = authManager.authenticate(
                        JwtAuthentication.unauthenticated(jwtAccessToken)
                );

                SecurityContext newContext = SecurityContextHolder.createEmptyContext();
                newContext.setAuthentication(authResult);
                SecurityContextHolder.setContext(newContext);
            } catch (AuthenticationException exception) {
                // do nothing
            }
        }

        filterChain.doFilter(request, response);
    }
}
