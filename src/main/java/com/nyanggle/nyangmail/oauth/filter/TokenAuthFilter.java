package com.nyanggle.nyangmail.oauth.filter;

import com.nyanggle.nyangmail.oauth.jwt.AuthTokenConverter;
import com.nyanggle.nyangmail.oauth.jwt.UserToken;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TokenAuthFilter extends OncePerRequestFilter {
    @Value("${nyangletter.auth.header.scheme}")
    private String TOKEN_SCHEME;
    private final AuthTokenConverter tokenConverter;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String tokenString = request.getHeader(this.TOKEN_SCHEME);
        if (!this.isEmptyString(tokenString)) {
            tokenConverter.validate(tokenString);
                UserToken userToken = tokenConverter.fromToken(tokenString);
            List<GrantedAuthority> auths = AuthorityUtils.createAuthorityList(
                    userToken.getRole());
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(userToken,
                    (Object) null, auths);
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

    boolean isEmptyString(String string) {
        return string == null || string.isEmpty();
    }
}
