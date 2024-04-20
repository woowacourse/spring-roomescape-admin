package roomescape.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationResponse;
import roomescape.dto.ReservationSaveRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static roomescape.TestFixture.*;

class ReservationControllerTest extends ControllerTest {

    @Test
    @DisplayName("예약 목록 GET 요청 시 상태코드 200을 반환한다.")
    void getReservations() throws Exception {
        // given
        ReservationTime expectedTime = new ReservationTime(1L, MIA_RESERVATION_TIME);
        Reservation expectedReservation = MIA_RESERVATION(expectedTime);

        BDDMockito.given(reservationService.getReservations())
                .willReturn(List.of(ReservationResponse.from(expectedReservation)));

        // when & then
        mockMvc.perform(get("/reservations").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value(USER_MIA))
                .andExpect(jsonPath("$[0].time.id").value(1L))
                .andExpect(jsonPath("$[0].time.startAt").value(MIA_RESERVATION_TIME.toString()))
                .andExpect(jsonPath("$[0].date").value(MIA_RESERVATION_DATE.toString()));
    }

    @Test
    @DisplayName("예약 POST 요청 시 상태코드 201을 반환한다.")
    void createReservation() throws Exception {
        // given
        ReservationSaveRequest request = new ReservationSaveRequest(USER_MIA, MIA_RESERVATION_DATE, 1L);

        BDDMockito.given(reservationService.createReservation(any()))
                .willReturn(1L);

        // when & then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/reservations/1"));
    }

    @Test
    @DisplayName("예약 DELETE 요청 시 상태코드 204을 반환한다.")
    void deleteReservation() throws Exception {
        // given
        BDDMockito.willDoNothing()
                .given(reservationService)
                .deleteReservation(anyLong());

        // when & then
        mockMvc.perform(delete("/reservations/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
