package com.nyanggle.nyangmail.oauth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class JwtExceptionFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        } catch(JwtException e) {
            setErrorResponse(request, response, e);
        }
    }
    private void setErrorResponse(HttpServletRequest request, HttpServletResponse response, JwtException e) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        final Map<String, Object> body = new HashMap<>();
        body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
        body.put("error", "UnAuthorized");
        body.put("message", e.getCause());
        body.put("path", request.getServletPath());
        objectMapper.writeValue(response.getOutputStream(), body);
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
