package roomescape.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.fixture.ReservationTimeFixture;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservationTimeResponse;

@WebMvcTest(ReservationTimeController.class)
class ReservationTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationTimeService reservationTimeService;

    @Test
    @DisplayName("예약을 추가하면 예약 정보를 응답받는다.")
    void createTime() throws Exception {
        // given
        String timeRequest = """
                {
                    "startAt": "10:00"
                }
                """;
        ReservationTimeResponse expected = new ReservationTimeResponse(ReservationTimeFixture.time1());
        when(reservationTimeService.create(any())).thenReturn(expected);

        // when & then
        String expectedResponse = """
                {
                    "id": 1,
                    "startAt": "10:00"
                }
                """;
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(timeRequest))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("모든 예약 조회시 형식에 맞게 응답한다.")
    void findAllTimes() throws Exception {
        // given
        List<ReservationTimeResponse> expected = List.of(
                new ReservationTimeResponse(ReservationTimeFixture.time1()),
                new ReservationTimeResponse(ReservationTimeFixture.time2()),
                new ReservationTimeResponse(ReservationTimeFixture.noReservationTime())
        );
        when(reservationTimeService.findAll()).thenReturn(expected);

        // when & then
        String expectedResponse = """
                [
                    {
                        "id": 1,
                        "startAt": "10:00"
                    },
                    {
                        "id": 2,
                        "startAt": "11:00"
                    },
                    {
                        "id": 3,
                        "startAt": "12:00"
                    }
                ]
                """;
        mockMvc.perform(get("/times"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("예약을 취소한다.")
    void cancelTime() throws Exception {
        mockMvc.perform(delete("/times/1"))
                .andExpect(status().isNoContent());
    }
}
