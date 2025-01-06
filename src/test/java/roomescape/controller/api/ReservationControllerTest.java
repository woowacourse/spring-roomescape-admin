package roomescape.controller.api;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import roomescape.fixture.MemberFixture;
import roomescape.fixture.ReservationFixture;
import roomescape.service.LoginService;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationResponse;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    private static final Cookie COOKIE = new Cookie("token", "token");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @MockBean
    private LoginService loginService;

    @BeforeEach
    void init() {
        when(loginService.findMemberByToken(any())).thenReturn(MemberFixture.member1());
    }

    @Test
    @DisplayName("예약을 추가하면 예약 정보를 응답받는다.")
    void createReservation() throws Exception {
        // given
        String reservationRequest = """
                {
                    "date": "2100-12-01",
                    "timeId": 2,
                    "themeId": 2
                }
                """;
        ReservationResponse expected = ReservationFixture.newResponse();
        when(reservationService.create(any(), any())).thenReturn(expected);

        // when & then
        String expectedResponse = """
                {
                    "id": 4,
                    "name": "kargo",
                    "date": "2100-12-01",
                    "time": {
                        "id": 2,
                        "startAt" : "11:00"
                    },
                    "theme": {
                        "id": 2,
                        "name": "theme2",
                        "description": "none",
                        "thumbnail": "none"
                    }
                }
                """;
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationRequest)
                        .cookie(COOKIE))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse));
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
        when(reservationService.findAll()).thenReturn(expected);

        // when & then
        String expectedResponse = """
                [
                    {
                        "id": 1,
                        "name": "kargo",
                        "date": "2100-12-01",
                        "time" : {
                            "id": 1,
                            "startAt" : "10:00"
                        },
                        "theme": {
                            "id": 1,
                            "name": "theme1",
                            "description": "none",
                            "thumbnail": "none"
                        }
                    },
                    {
                        "id": 2,
                        "name": "solar",
                        "date": "2100-12-01",
                        "time": {
                            "id": 1,
                            "startAt" : "10:00"
                        },
                        "theme": {
                            "id": 2,
                            "name": "theme2",
                            "description": "none",
                            "thumbnail": "none"
                        }
                    },
                    {
                        "id": 3,
                        "name": "hotea",
                        "date": "2100-12-01",
                        "time" : {
                            "id": 2,
                            "startAt" : "11:00"
                        },
                        "theme": {
                            "id": 1,
                            "name": "theme1",
                            "description": "none",
                            "thumbnail": "none"
                        }
                    }
                ]
                """;
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelReservation() throws Exception {
        mockMvc.perform(delete("/reservations/1"))
                .andExpect(status().isNoContent());
    }
}
