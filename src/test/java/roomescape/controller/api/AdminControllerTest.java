package roomescape.controller.api;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import jakarta.servlet.http.Cookie;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.fixture.ReservationFixture;
import roomescape.service.LoginService;
import roomescape.service.ReservationService;
import roomescape.service.dto.ReservationAdminRequest;
import roomescape.service.dto.ReservationResponse;

@WebMvcTest(AdminController.class)
class AdminControllerTest {

    private static final Cookie COOKIE = new Cookie("token", "token");

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;
    @MockBean
    private LoginService loginService;

    @Test
    @DisplayName("관리자 예약 생성에 성공하면 201을 반환한다.")
    void createReservation() throws Exception {
        // given
        String adminRequest = """
                {
                    "date": "2100-12-01",
                    "timeId": 2,
                    "themeId": 2,
                    "memberId": 1
                }
                """;
        ReservationResponse expected = ReservationFixture.newResponse();
        when(reservationService.create(any(ReservationAdminRequest.class), any())).thenReturn(expected);

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
                        .content(adminRequest)
                        .cookie(COOKIE))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse));
    }
}
