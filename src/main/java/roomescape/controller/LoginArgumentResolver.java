package roomescape.controller;

import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import roomescape.exception.BadRequestException;
import roomescape.service.LoginService;

@RequiredArgsConstructor
public class LoginArgumentResolver implements HandlerMethodArgumentResolver {

    private final LoginService loginService;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(Login.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String token = getToken(request);

        return loginService.findMemberByToken(token);
    }

    private String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new BadRequestException("쿠키가 존재하지 않습니다.");
        }
        return Arrays.stream(cookies)
                .filter(c -> Objects.equals(c.getName(), LOGIN_TOKEN_HEADER_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new BadRequestException("로그인을 위한 토큰을 쿠키에 추가해주세요."));
    }
}
