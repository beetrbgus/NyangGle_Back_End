package com.nyanggle.nyangmail.oauth;

import com.nyanggle.nyangmail.config.AuthUser;
import com.nyanggle.nyangmail.exception.user.UnAuthorizedException;
import com.nyanggle.nyangmail.oauth.dto.UserPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

public class CustomAuthArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        AuthUser authUser = parameter.getParameterAnnotation(AuthUser.class);
        if(authUser != null) {
            return parameter.getParameterType().equals(UserPrincipal.class);
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        Object principal = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication != null) {
            principal = authentication.getPrincipal();
        }
        if(principal == null || principal.getClass() == String.class) {
            throw new UnAuthorizedException();
        }
        return principal;
    }
}
