package roomescape.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.core.service.ReservationService;
import roomescape.core.service.request.ReservationRequestDto;
import roomescape.core.service.response.ReservationResponseDto;
import roomescape.core.service.response.ReservationTimeResponseDto;
import roomescape.web.controller.request.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @DisplayName("예약 목록 조회 요청 시, 200 OK를 응답한다")
    @Test
    void findAll() throws Exception {
        when(reservationService.findAll())
                .thenReturn(List.of());

        this.mvc.perform(get("/reservations"))
                .andExpect(status().isOk());
    }

    @DisplayName("예약 추가 요청 시, 201 Created를 응답한다")
    @Test
    void save() throws Exception {
        when(reservationService.save(any(ReservationRequestDto.class)))
                .thenReturn(new ReservationResponseDto(
                        1L,
                        "비밥",
                        LocalDate.now().plusDays(1),
                        new ReservationTimeResponseDto(1L, LocalTime.of(9, 0))));

        String requestBody = objectMapper.writeValueAsString(new ReservationRequest(
                "비밥",
                LocalDate.now().plusDays(1),
                1L));

        this.mvc.perform(post("/reservations")
                        .content(requestBody)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @DisplayName("삭제 요청 시, 204 No Content를 응답한다")
    @Test
    void delete_() throws Exception {
        this.mvc.perform(delete("/reservations/" + 1))
                .andExpect(status().isNoContent());
    }
}
