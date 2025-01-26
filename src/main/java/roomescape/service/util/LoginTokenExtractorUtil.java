package roomescape.service.util;

import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Objects;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;

public abstract class LoginTokenExtractorUtil {

    public static String getToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies == null) {
            throw new BadRequestException(ErrorCode.NOT_EXIST_TOKEN);
        }
        return Arrays.stream(cookies)
                .filter(c -> Objects.equals(c.getName(), LOGIN_TOKEN_HEADER_NAME))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new BadRequestException(ErrorCode.NOT_EXIST_TOKEN));
    }
}
