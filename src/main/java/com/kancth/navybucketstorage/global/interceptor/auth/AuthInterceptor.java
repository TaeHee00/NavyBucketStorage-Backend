package com.kancth.navybucketstorage.global.interceptor.auth;

import com.kancth.navybucketstorage.domain.security.exception.InsufficientPermissionException;
import com.kancth.navybucketstorage.domain.security.exception.InvalidTokenException;
import com.kancth.navybucketstorage.domain.security.service.JwtService;
import com.kancth.navybucketstorage.domain.user.entity.User;
import com.kancth.navybucketstorage.domain.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.lang.annotation.Annotation;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Component
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtService jwtService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Auth auth = getAnnotation((HandlerMethod) handler, Auth.class);

        if (auth == null) {
            return HandlerInterceptor.super.preHandle(request, response, handler);
        }

        AuthType authType = auth.authType();

        switch (authType) {
            case NONE -> {
                log.info("none");
            }
            case USER, ADMIN -> {
                final String authToken = this.getAuthToken(request);
                // FIXME: AccessToken 이거 redis에 넣으면 안됨 애초에 그럴려고 쓰는거
                // FIXME: passwordEncoding 필요함 회원가입할때 넣자
                User user = jwtService.getUser(authToken);

                this.checkPermission(user, AuthType.USER);
                request.setAttribute("currentUser", user);
            }
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    private <A extends Annotation> A getAnnotation(HandlerMethod handlerMethod, Class<A> annotationType) {
        return Optional.ofNullable(handlerMethod.getMethodAnnotation(annotationType))
                .orElse(handlerMethod.getBeanType().getAnnotation(annotationType));
    }

    private String getAuthToken(HttpServletRequest request) {
        return Optional.of(request.getHeader("Authorization")).orElseThrow(InvalidTokenException::new);
    }

    private void checkPermission(User user, AuthType authType) {
        if (!(user.getAuthType().equals(AuthType.ADMIN) || user.getAuthType().equals(authType))) {
            throw new InsufficientPermissionException();
        }
    }
}