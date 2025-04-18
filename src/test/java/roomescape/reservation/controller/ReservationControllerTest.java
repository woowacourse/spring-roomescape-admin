package roomescape.reservation.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.BDDMockito.willThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.reservation.dto.ReservationRequestDto;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.exception.EntityNotFoundException;
import roomescape.reservation.repository.ReservationRepository;

@WebMvcTest(controllers = ReservationController.class)
class ReservationControllerTest {

    @MockitoBean
    ReservationRepository reservationRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("모든 예약 정보를 가져온다.")
    @Test
    void test1() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.now();

        List<Reservation> reservations = List.of(
                new Reservation(1, "꾹", now),
                new Reservation(2, "꾹", now),
                new Reservation(3, "꾹", now)
        );

        given(reservationRepository.findAll()).willReturn(reservations);

        // when & then
        mvc.perform(get("/reservations"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3))
                .andExpect(jsonPath("$[0].name").value("꾹"));
    }

    @DisplayName("예약 정보를 추가한다.")
    @ParameterizedTest
    @CsvSource(value = {"꾹,2025-04-17,10:00"}, delimiter = ',')
    void test2(String name, String date, String time) throws Exception {

        // given
        LocalDate localDate = LocalDate.parse(date);
        LocalTime localTime = LocalTime.parse(time);
        LocalDateTime localDateTime = LocalDateTime.of(localDate, localTime);

        ReservationRequestDto requestDto = new ReservationRequestDto(name, localDate, localTime);
        Reservation reservation = new Reservation(1, name, localDateTime);
        given(reservationRepository.save(any())).willReturn(reservation);

        String json = objectMapper.writeValueAsString(requestDto);

        // when & then
        mvc.perform(post("/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andExpect(status().isOk());
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Test
    void test3() throws Exception {
        // given
        long id = 1L;

        willDoNothing().given(reservationRepository).deleteById(id);

        // when & then
        mvc.perform(delete("/reservations/{id}", id))
                .andExpect(status().isNoContent());
    }

    @DisplayName("삭제할 예약 정보가 없다면 404 에러 코드를 반환한다")
    @Test
    void test4() throws Exception {
        // given
        long id = 1L;

        willThrow(new EntityNotFoundException(""))
                .given(reservationRepository).deleteById(id);

        // when & then
        mvc.perform(delete("/reservations/{id}", id))
                .andExpect(status().isNotFound());
    }

}
