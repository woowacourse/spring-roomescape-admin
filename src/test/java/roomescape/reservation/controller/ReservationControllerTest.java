package roomescape.reservation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.dto.ReservationResponseDto;
import roomescape.reservation.dto.ResponseCode;
import roomescape.reservation.service.ReservationService;
import roomescape.time.dto.ReservationTimeResponseDto;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationController.class)
class ReservationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationService reservationService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("모든 예약을 조회할 수 있다. - controller")
    @Test
    public void findAll() throws Exception {
        ReservationResponseDto firstResponseDto = new ReservationResponseDto(1, "hotea", "2024-02-21", new ReservationTimeResponseDto(1, "10:00"));
        ReservationResponseDto secondResponseDto = new ReservationResponseDto(2, "zeus", "2024-02-22", new ReservationTimeResponseDto(1, "10:00"));
        List<ReservationResponseDto> responseDtoList = List.of(firstResponseDto, secondResponseDto);

        Mockito.when(reservationService.findAll())
               .thenReturn(responseDtoList);

        mockMvc.perform(get("/reservations"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(responseDtoList)));
    }

    @DisplayName("예약을 저장할 수 있다. - controller")
    @Test
    void save() throws Exception {
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto("hotea", "2024-02-21", 1);
        ReservationResponseDto reservationResponseDto = new ReservationResponseDto(1, "hotea", "2024-02-21", new ReservationTimeResponseDto(1, "10:00"));

        Mockito.when(reservationService.save(reservationRequestDto))
               .thenReturn(reservationResponseDto);

        mockMvc.perform(post("/reservations")
                       .content(objectMapper.writeValueAsString(reservationRequestDto))
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(reservationResponseDto)));
    }

    @ParameterizedTest
    @EnumSource(ResponseCode.class)
    @DisplayName("예약을 삭제할 수 있다. - controller")
    void delete(final ResponseCode responseCode) throws Exception {
        long id = 1;

        Mockito.when(reservationService.deleteById(id))
               .thenReturn(responseCode);

        mockMvc.perform(MockMvcRequestBuilders.delete("/reservations/{id}", id))
               .andExpect(status().is(responseCode.getHttpStatus().value()));
    }
}
