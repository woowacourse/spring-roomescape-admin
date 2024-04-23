package roomescape.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.ReservationTime;
import roomescape.dto.ReservationTimeResponse;
import roomescape.service.ReservationTimeService;

@WebMvcTest(ReservationTimeController.class)
class ReservationTimeControllerTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationTimeService reservationTimeService;

    @Test
    void getAllReservationTimes() throws Exception {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 30));

        BDDMockito.given(reservationTimeService.getAllReservationTimes())
                .willReturn(List.of(ReservationTimeResponse.from(reservationTime)));

        mockMvc.perform(get("/times"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(reservationTime.getId()))
                .andExpect(jsonPath("$[0].startAt").value(reservationTime.getStartAt().toString()));
    }

    @Test
    void addReservationTime() throws Exception {
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.of(10, 30));

        BDDMockito.given(reservationTimeService.addReservationTime(any()))
                .willReturn(ReservationTimeResponse.from(reservationTime));

        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(reservationTime)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/times/" + reservationTime.getId()))
                .andExpect(jsonPath("$.id").value(reservationTime.getId()))
                .andExpect(jsonPath("$.startAt").value(reservationTime.getStartAt().toString()));
    }

    @Test
    void deleteReservationTime() throws Exception {
        BDDMockito.willDoNothing()
                .given(reservationTimeService)
                .deleteReservationTimeById(anyLong());

        mockMvc.perform(delete("/times/{id}", anyLong()))
                .andDo(print())
                .andExpect(status().isNoContent());
    }
}
