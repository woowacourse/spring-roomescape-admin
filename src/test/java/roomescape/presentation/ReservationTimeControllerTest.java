package roomescape.presentation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.http.MediaType;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeResponse;
import roomescape.dto.ReservationTimeSaveRequest;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static roomescape.TestFixture.MIA_RESERVATION_TIME;

class ReservationTimeControllerTest extends ControllerTest {

    @Test
    @DisplayName("예약 시간 POST 요청 시 상태코드 200을 반환한다.")
    void createReservationTime() throws Exception {
        // given
        ReservationTimeSaveRequest request = new ReservationTimeSaveRequest(MIA_RESERVATION_TIME);
        ReservationTimeResponse expectedResponse = ReservationTimeResponse.from(new ReservationTime(1L, request.toModel()));

        BDDMockito.given(reservationTimeService.create(any()))
                .willReturn(expectedResponse);

        // when & then
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(request)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.startAt").value(MIA_RESERVATION_TIME.toString()));
    }

    @Test
    @DisplayName("예약 시간 목록 GET 요청 시 상태코드 200을 반환한다.")
    void getReservationTimes() throws Exception {
        // given
        BDDMockito.given(reservationTimeService.getReservationTimes())
                .willReturn(List.of(ReservationTimeResponse.from(new ReservationTime(MIA_RESERVATION_TIME))));

        // when & then
        mockMvc.perform(get("/times").contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].startAt").value(MIA_RESERVATION_TIME.toString()));
    }

    @Test
    @DisplayName("예약 시간 DELETE 요청 시 상태코드 200를 반환한다.")
    void deleteReservationTime() throws Exception {
        // given
        BDDMockito.willDoNothing()
                .given(reservationTimeService)
                .deleteReservationTime(anyLong());

        // when & then
        mockMvc.perform(delete("/times/{id}", anyLong())
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
