package roomescape.time.controller;

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
import roomescape.reservation.dto.ResponseCode;
import roomescape.time.dto.ReservationTimeRequestDto;
import roomescape.time.dto.ReservationTimeResponseDto;
import roomescape.time.service.ReservationTimeService;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationTimeController.class)
class ReservationTimeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReservationTimeService reservationTimeService;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("모든 예약 가능 시간을 조회할 수 있다. - controller")
    @Test
    public void findAll() throws Exception {
        ReservationTimeResponseDto firstResponseDto = new ReservationTimeResponseDto(1, "10:00");
        ReservationTimeResponseDto secondResponseDto = new ReservationTimeResponseDto(2, "10:10");
        List<ReservationTimeResponseDto> responseDtoList = List.of(firstResponseDto, secondResponseDto);

        Mockito.when(reservationTimeService.findAll())
               .thenReturn(responseDtoList);

        mockMvc.perform(get("/times"))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(responseDtoList)));
    }

    @DisplayName("예약 가능 시간을 저장할 수 있다. - controller")
    @Test
    void save() throws Exception {
        ReservationTimeRequestDto reservationTimeRequestDto = new ReservationTimeRequestDto("10:00");
        ReservationTimeResponseDto reservationTimeResponseDto = new ReservationTimeResponseDto(1, "10:00");

        Mockito.when(reservationTimeService.save(reservationTimeRequestDto))
               .thenReturn(reservationTimeResponseDto);

        mockMvc.perform(post("/times")
                       .content(objectMapper.writeValueAsString(reservationTimeRequestDto))
                       .contentType(MediaType.APPLICATION_JSON)
                       .accept(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(reservationTimeResponseDto)));
    }

    @ParameterizedTest
    @EnumSource(ResponseCode.class)
    @DisplayName("예약 가능 시간을 삭제할 수 있다. - controller")
    void delete(final ResponseCode responseCode) throws Exception {
        long id = 1;

        Mockito.when(reservationTimeService.deleteById(id))
               .thenReturn(responseCode);

        mockMvc.perform(MockMvcRequestBuilders.delete("/times/{id}", id))
               .andExpect(status().is(responseCode.getHttpStatus().value()));
    }
}
