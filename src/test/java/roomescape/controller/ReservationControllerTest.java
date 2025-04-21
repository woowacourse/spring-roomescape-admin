package roomescape.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.dto.ReservationRequest;
import roomescape.dto.ReservationResponse;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /reservations - 모든 예약 목록을 조회한다")
    void read_all_reservations() throws Exception {
        // when & then
        mockMvc.perform(get("/reservations"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /reservations - 예약을 생성한다")
    void create_reservation() throws Exception {
        // given
        LocalDate date = LocalDate.of(2025, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        ReservationRequest reservationRequest = new ReservationRequest("kim", date, time);
        String json = objectMapper.writeValueAsString(reservationRequest);

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.name").value("kim"),
                        jsonPath("$.date").value("2025-04-21"),
                        jsonPath("$.time").value("10:00:00")
                );
    }

    @Test
    @DisplayName("DELETE /reservations/{id} - id에 해당하는 예약을 삭제한다")
    void delete_reservation() throws Exception {
        // given
        LocalDate date = LocalDate.of(2025, 4, 21);
        LocalTime time = LocalTime.of(10, 0);
        ReservationRequest reservationRequest = new ReservationRequest("kim", date, time);
        String json = objectMapper.writeValueAsString(reservationRequest);
        String response = mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse()
                .getContentAsString();
        ReservationResponse reservationResponse = objectMapper.readValue(response, ReservationResponse.class);
        Long createdId = reservationResponse.id();

        // when & then
        String urlTemplate = "/reservations/%d".formatted(createdId);
        mockMvc.perform(delete(urlTemplate))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("DELETE /reservations/{id} - 존재하지 않는 예약 삭제 시 400 반환")
    void deleteNotExistReservation() throws Exception {
        mockMvc.perform(delete("/reservations/2"))
                .andExpect(status().isBadRequest());
    }
}
