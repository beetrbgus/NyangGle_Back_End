package com.nyanggle.nyangmail.oauth;

import com.nyanggle.nyangmail.common.CookieUtils;
import com.nyanggle.nyangmail.config.HttpCookieOAuth2AuthorizationRequestRepository;
import com.nyanggle.nyangmail.oauth.jwt.AuthTokenConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    @Value("${nyangletter.auth.header.scheme}")
    private String TOKEN_SCHEME;

    @Value("${nyangletter.auth.redirect.whenLoginSuccess.uri}")
    private String REDIRECT_URI;

    private final AuthTokenConverter authTokenConverter;
    private final HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        UserPrincipal principal = (UserPrincipal)authentication.getPrincipal();

        String redirectUrl = REDIRECT_URI + principal.getUserId();; //회원의 붕어빵 카트로 바로 이동
        setCookie(response, authentication);

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private void setCookie(HttpServletResponse response,
                           Authentication authentication) {
        String token = authTokenConverter.toTokenString(authentication);

        CookieUtils.addCookie(response, TOKEN_SCHEME, token, 60 * 60);
    }

    private void setResponseHeader(HttpServletRequest request, HttpServletResponse response,
                                   Authentication authentication) {
        String token = authTokenConverter.toTokenString(authentication);

        response.setHeader(TOKEN_SCHEME, token);
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request,
                                                 HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request,
                response);
    }
}
