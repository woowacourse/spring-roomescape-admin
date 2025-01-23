package roomescape.controller.api;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import roomescape.domain.Member;
import roomescape.service.LoginService;
import roomescape.service.dto.LoginRequest;
import roomescape.service.dto.LoginResponse;
import roomescape.service.dto.MemberResponse;

@Slf4j
@RequiredArgsConstructor
@RestController
public class LoginController {

    public static final String LOGIN_TOKEN_HEADER_NAME = "token";

    @Value("${jwt.expirationInSeconds}")
    private long expirationInSeconds;

    private final LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = loginService.login(loginRequest);

        ResponseCookie responseCookie = ResponseCookie.from(LOGIN_TOKEN_HEADER_NAME, loginResponse.token())
                .httpOnly(true)
                .path("/")
                .maxAge(expirationInSeconds)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .build();
    }

    @GetMapping("/login/check")
    public MemberResponse loginCheck(@CookieValue(name = LOGIN_TOKEN_HEADER_NAME) String token) {
        Member member = loginService.findMemberByToken(token);
        return new MemberResponse(member);
    }

    @PostMapping("/logout")
    public void logout(HttpServletResponse response) {
        Cookie cookie = new Cookie(LOGIN_TOKEN_HEADER_NAME, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
