package roomescape.controller.api;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static roomescape.controller.api.LoginController.LOGIN_TOKEN_HEADER_NAME;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.Member;
import roomescape.domain.ReservationStatus;
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.service.LoginService;
import roomescape.service.ReservationFacadeService;
import roomescape.service.dto.ReservationMineResponse;
import roomescape.service.dto.ReservationRequest;
import roomescape.service.dto.ReservationResponse;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    private static final String MEMBER_1_TOKEN = "member1TokenValue";
    private static final Cookie COOKIE = new Cookie(LOGIN_TOKEN_HEADER_NAME, MEMBER_1_TOKEN);

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationFacadeService reservationFacadeService;

    @MockBean
    private LoginService loginService;

    private Member member = MemberFixture.member1();

    @BeforeEach
    void init() {
        when(loginService.findMemberByToken(MEMBER_1_TOKEN)).thenReturn(member);
    }

    @Test
    @DisplayName("예약 요청이 API 스펙에 맞게 오면 API 스펙에 맞는 응답을 내리며, 쿠키에서 로그인을 위한 토큰을 파싱할 수 있다.")
    void createReservation() throws Exception {
        // given
        String requestString = """
                {
                    "date": "2100-12-01",
                    "timeId": 2,
                    "themeId": 2,
                    "status": "예약"
                }
                """;

        ReservationRequest request = objectMapper.readValue(requestString, ReservationRequest.class);
        ReservationResponse expected = ReservationFixture.newResponse(ReservationStatus.RESERVED);
        when(reservationFacadeService.createReservation(request, member)).thenReturn(expected);

        // when & then
        String expectedResponse = """
                {
                    "id": 6,
                    "date": "2100-12-01",
                    "time": {
                        "id": 2,
                        "startAt" : "11:00"
                    },
                    "member": {
                        "id": 1,
                        "name": "kargo"
                    },
                    "theme": {
                        "id": 2,
                        "name": "theme2",
                        "description": "none",
                        "thumbnail": "none"
                    },
                    "status": "예약"
                }
                """;
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString)
                        .cookie(COOKIE))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    @DisplayName("예약 대기 요청이 API 스펙에 맞게 오면 API 스펙에 맞는 응답을 내리며, 쿠키에서 로그인을 위한 토큰을 파싱할 수 있다.")
    void createWaiting() throws Exception {
        // given
        String requestString = """
                {
                    "date": "2100-12-01",
                    "timeId": 2,
                    "themeId": 2,
                    "status": "대기"
                }
                """;

        ReservationRequest request = objectMapper.readValue(requestString, ReservationRequest.class);
        ReservationResponse expected = ReservationFixture.newResponse(ReservationStatus.WAITING);
        when(reservationFacadeService.createReservation(request, member)).thenReturn(expected);

        // when & then
        String expectedResponse = """
                {
                    "id": 6,
                    "date": "2100-12-01",
                    "time": {
                        "id": 2,
                        "startAt" : "11:00"
                    },
                    "member": {
                        "id": 1,
                        "name": "kargo"
                    },
                    "theme": {
                        "id": 2,
                        "name": "theme2",
                        "description": "none",
                        "thumbnail": "none"
                    },
                    "status": "대기"
                }
                """;
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestString)
                        .cookie(COOKIE))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    @DisplayName("모든 예약 조회시 형식에 맞게 응답한다.")
    void findAllReservations() throws Exception {
        // given
        List<ReservationResponse> expected = List.of(
                new ReservationResponse(ReservationFixture.reservation1()),
                new ReservationResponse(ReservationFixture.reservation2()),
                new ReservationResponse(ReservationFixture.reservation3())
        );
        when(reservationFacadeService.findAll()).thenReturn(expected);

        // when & then
        String expectedResponse = """
                [
                    {
                        "id": 1,
                        "date": "2100-12-01",
                        "member": {
                            "id": 1,
                            "name": "kargo"
                        },
                        "time" : {
                            "id": 1,
                            "startAt" : "10:00"
                        },
                        "theme": {
                            "id": 1,
                            "name": "theme1",
                            "description": "none",
                            "thumbnail": "none"
                        },
                        "status": "예약"
                    },
                    {
                        "id": 2,
                        "date": "2100-12-01",
                        "member": {
                            "id": 2,
                            "name": "solar"
                        },
                        "time": {
                            "id": 1,
                            "startAt" : "10:00"
                        },
                        "theme": {
                            "id": 2,
                            "name": "theme2",
                            "description": "none",
                            "thumbnail": "none"
                        },
                        "status": "예약"
                    },
                    {
                        "id": 3,
                        "date": "2100-12-01",
                        "member": {
                            "id": 3,
                            "name": "hotea"
                        },
                        "time" : {
                            "id": 2,
                            "startAt" : "11:00"
                        },
                        "theme": {
                            "id": 1,
                            "name": "theme1",
                            "description": "none",
                            "thumbnail": "none"
                        },
                        "status": "예약"
                    }
                ]
                """;
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    @DisplayName("특정 회원의 예약 조회시 형식에 맞게 응답한다.")
    void findReservationsOfMember() throws Exception {
        // given
        List<ReservationMineResponse> expected = List.of(
                new ReservationMineResponse(ReservationFixture.reservation1())
        );
        when(reservationFacadeService.findOfMember(member)).thenReturn(expected);

        // when & then
        String expectedResponse = """
                [
                    {
                        "reservationId": 1,
                        "themeName": "theme1",
                        "date": "2100-12-01",
                        "startAt": "10:00",
                        "status": "예약"
                    }
                ]
                """;
        mockMvc.perform(get("/reservations/mine").cookie(COOKIE))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse, true));
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelReservation() throws Exception {
        mockMvc.perform(delete("/reservations/1")
                        .cookie(COOKIE))
                .andExpect(status().isNoContent());
    }
}
