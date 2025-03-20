package com.jonghwan.typing.shared.security.member;

import com.jonghwan.typing.shared.exception.custom.ForbiddenException;
import com.jonghwan.typing.shared.security.jwt.JwtUserDetails;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class) && parameter.getParameterType().equals(LoginMember.class);
    }

    @Override
    public Object resolveArgument(@NotNull MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  @NotNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) {
            throw new ForbiddenException("Authentication is needed");
        }
        if(!(auth.getPrincipal() instanceof JwtUserDetails userDetails)){
            throw new ForbiddenException("Invalid authentication principal");
        }

        return LoginMember.builder()
                .id(Long.parseLong(userDetails.getUsername()))
                .role(userDetails.getAuthorities().iterator().next().getAuthority())
                .build();
    }
}
