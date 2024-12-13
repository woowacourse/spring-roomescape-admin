package roomescape.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.service.ReservationTimeService;
import roomescape.service.dto.ReservatonTimeResponse;

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
        ReservatonTimeResponse expected = new ReservatonTimeResponse(1L, LocalTime.of(10, 00));
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
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("모든 예약 조회시 형식에 맞게 응답한다.")
    void findAllTimes() throws Exception {
        // given
        List<ReservatonTimeResponse> expected = List.of(
                new ReservatonTimeResponse(1L, LocalTime.of(10, 0)),
                new ReservatonTimeResponse(2L, LocalTime.of(11, 0))
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
                .andExpect(status().isOk());
    }
}
