package roomescape.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;
import roomescape.exception.BadRequestException;
import roomescape.exception.ErrorCode;
import roomescape.service.LoginService;
import roomescape.service.util.LoginTokenExtractorUtil;

@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final LoginService loginService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String token = LoginTokenExtractorUtil.getToken(request);
        if (loginService.isAdminToken(token)) {
            return true;
        }
        throw new BadRequestException(ErrorCode.FORBIDDEN_ADMIN_ACCESS);
    }
}
