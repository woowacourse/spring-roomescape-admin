package roomescape.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.service.ReservationResponse;
import roomescape.service.ReservationService;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Test
    @DisplayName("예약을 추가하면 예약 정보를 응답받는다.")
    void createReservation() throws Exception {
        // given
        String reservationRequest = """
                {
                    "date": "2023-08-05",
                    "name": "브라운",
                    "time": "15:40"
                }
                """;
        ReservationResponse expected = new ReservationResponse(1L, "브라운", LocalDate.of(2023, 8, 5),
                LocalTime.of(15, 40));
        when(reservationService.create(any())).thenReturn(expected);

        // when & then
        String expectedResponse = """
                {
                    "id": 1,
                    "name": "브라운",
                    "date": "2023-08-05",
                    "time": "15:40"
                }
                """;
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(reservationRequest))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }

    @Test
    @DisplayName("모든 예약 조회시 형식에 맞게 응답한다.")
    void findAllReservations() throws Exception {
        // given
        List<ReservationResponse> expected = List.of(
                new ReservationResponse(1L, "브라운", LocalDate.of(2023, 1, 1), LocalTime.of(10, 0)),
                new ReservationResponse(2L, "브라운", LocalDate.of(2023, 1, 2), LocalTime.of(11, 0))
        );
        when(reservationService.findAll()).thenReturn(expected);

        // when & then
        String expectedResponse = """
                [
                    {
                        "id": 1,
                        "name": "브라운",
                        "date": "2023-01-01",
                        "time": "10:00"
                    },
                    {
                        "id": 2,
                        "name": "브라운",
                        "date": "2023-01-02",
                        "time": "11:00"
                    }
                ]
                """;
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResponse));
    }
}
