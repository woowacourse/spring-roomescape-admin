package roomescape.service.util;

import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import roomescape.exception.BadRequestException;

public abstract class LoginTokenExtractorUtil {

    public static String getToken(HttpServletRequest request) {
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
