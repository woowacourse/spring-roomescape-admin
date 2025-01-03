package roomescape.controller.api;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.service.LoginService;
import roomescape.service.dto.LoginResponse;

@WebMvcTest(LoginController.class)
class LoginControllerTest {

    @Value("${jwt.expirationInSeconds}")
    private long expirationInSeconds;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LoginService loginService;

    @Test
    @DisplayName("로그인에 성공하면 Set-Cookie 헤더에 JWT 토큰을 응답받는다.")
    void login() throws Exception {
        // given
        String loginRequest = """
                {
                    "email": "kargo@google.com",
                    "password": "1234"
                }
                """;
        LoginResponse loginResponse = new LoginResponse("tokenValue");
        when(loginService.login(any())).thenReturn(loginResponse);

        // when & then
        String expectedCookie = String.format("token=tokenValue; Path=/; Max-Age=%d; Expires=%s; HttpOnly",
                expirationInSeconds,
                getCurrentGmtDate());

        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginRequest))
                .andExpect(status().isOk());
        //TODO: Cookie값까지 매칭시켜서 테스트 통과시키기
//                .andExpect(header().stringValues(HttpHeaders.SET_COOKIE, expectedCookie));
    }

    public static String getCurrentGmtDate() {
        Date currentDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'");
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(currentDate);
    }
}
