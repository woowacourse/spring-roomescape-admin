package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import roomescape.domain.Reservation;
import roomescape.dto.ReservationSaveRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReservationControllerTest extends ControllerTest {
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("예약 목록 GET 요청 시 상태코드 200을 반환한다.")
    void getReservations() throws Exception {
        // given
        BDDMockito.given(reservationService.getReservations())
                .willReturn(List.of(new Reservation("미아", LocalDate.now(), LocalTime.now())));

        // when & then
        mockMvc.perform(get("/reservations").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("미아"))
                .andExpect(jsonPath("$[0].date").value(LocalDate.now().toString()));
    }

    @Test
    @DisplayName("예약 POST 요청 시 상태코드 200을 반환한다.")
    void createReservation() throws Exception {
        // given
        ReservationSaveRequest request = new ReservationSaveRequest("미아", LocalDate.now(), LocalTime.now());
        Reservation expectedReservation = request.toReservation();

        BDDMockito.given(reservationService.createReservation(any()))
                .willReturn(expectedReservation);

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(expectedReservation.getName()))
                .andExpect(jsonPath("$.date").value(expectedReservation.getDate().toString()));
    }

    @Test
    @DisplayName("예약 DELETE 요청 시 상태코드 200을 반환한다.")
    void deleteReservation() throws Exception {
        // given
        BDDMockito.willDoNothing()
                .given(reservationService)
                .deleteReservation(anyLong());

        // when & then
        mockMvc.perform(delete("/reservations/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
