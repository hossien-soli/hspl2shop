package dev.hspl.hspl2shop.common.web.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jspecify.annotations.NullMarked;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@NullMarked
public class TestServletFilter extends OncePerRequestFilter {
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        System.out.println("[HTTP] [URI] " + request.getRequestURI());
        System.out.println("[HTTP] [QueryString] " + request.getQueryString());
        System.out.println("[HTTP] [Attributes] " + Collections.list(request.getAttributeNames()));
        System.out.println("[HTTP] [Headers] " + Collections.list(request.getHeaderNames()));
        System.out.println("[HTTP] [METHOD] " + request.getMethod());
        System.out.println("[HTTP] [ParameterMap Values] " + request.getParameterMap().values());
        System.out.println("[HTTP] [Protocol] " + request.getProtocol());
        //System.out.println("[HTTP] [RawRequest] " + new String(request.getInputStream().readAllBytes()));
        System.out.println("[HTTP] [RequestCount] " + requestCounter.incrementAndGet());

        //System.out.println(request.getReader().lines().toList());

        filterChain.doFilter(request, response);
    }
}
