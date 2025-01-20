package roomescape.controller.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.Member;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.service.LoginService;
import roomescape.service.ReservationFacadeService;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationResponse;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    //TODO: ControllerTest로 상수를 몰아 넣을지 고려
    private static final String MEMBER_1_TOKEN = "member1TokenValue";
    private static final String MEMBER_2_TOKEN = "member2TokenValue";
    private static final Cookie COOKIE1 = new Cookie(LOGIN_TOKEN_HEADER_NAME, MEMBER_1_TOKEN);
    private static final Cookie COOKIE2 = new Cookie(LOGIN_TOKEN_HEADER_NAME, MEMBER_2_TOKEN);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationFacadeService reservationFacadeService;

    @MockBean
    private LoginService loginService;

    private Member member1 = MemberFixture.member1();
    private Member member2 = MemberFixture.member2();

    @BeforeEach
    void init() {
        when(loginService.findMemberByToken(MEMBER_1_TOKEN)).thenReturn(member1);
        when(loginService.findMemberByToken(MEMBER_2_TOKEN)).thenReturn(member2);

        when(loginService.isAdminToken(MEMBER_1_TOKEN)).thenReturn(true);
        when(loginService.isAdminToken(MEMBER_2_TOKEN)).thenReturn(false);
    }

    @Test
    @DisplayName("관리자 예약 생성에 성공하면 201을 반환한다.")
    void createReservation() throws Exception {
        // given
        String requestString = """
                {
                    "date": "2100-12-01",
                    "timeId": 2,
                    "themeId": 2,
                    "memberId": 1
                }
                """;
        ReservationAdminRequest request = objectMapper.readValue(requestString, ReservationAdminRequest.class);
        ReservationResponse response = ReservationFixture.newResponse();
        when(reservationFacadeService.createReservationByAdmin(request)).thenReturn(response);

        // when & then
        String expectedResponse = """
                {
                    "id": 4,
                    "date": "2100-12-01",
                    "time": {
                        "id": 2,
                        "startAt" : "11:00"
                    },
                    "member": {
                        "name": "kargo"
                    },
                    "theme": {
                        "id": 2,
                        "name": "theme2",
                        "description": "none",
                        "thumbnail": "none"
                    }
                }
                """;
        mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString)
                        .cookie(COOKIE1))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("관리자가 아니면 관리자 예약에 실패한다.")
    void createReservationFail() throws Exception {
        // given
        String requestString = """
                {
                    "date": "2100-12-01",
                    "timeId": 2,
                    "themeId": 2,
                    "memberId": 1
                }
                """;
        ReservationAdminRequest request = objectMapper.readValue(requestString, ReservationAdminRequest.class);
        ReservationResponse response = ReservationFixture.newResponse();
        when(reservationFacadeService.createReservationByAdmin(request)).thenReturn(response);

        // when & then
        mockMvc.perform(post("/admin/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString)
                        .cookie(COOKIE2))
                .andExpect(status().isBadRequest());
    }
}
