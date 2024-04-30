package roomescape.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import roomescape.domain.ClientName;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;
import roomescape.dto.SaveReservationRequest;
import roomescape.dto.SaveReservationTimeRequest;
import roomescape.service.ReservationService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.doThrow;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ReservationApiController.class)
class ReservationApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReservationService reservationService;

    @DisplayName("전체 예약 정보를 조회한다.")
    @Test
    void getReservationsTest() throws Exception {
        // Given
        ReservationTime reservationTime = new ReservationTime(1L, LocalTime.now().plusHours(3));
        List<Reservation> reservations = List.of(
                new Reservation(1L, new ClientName("켈리"), LocalDate.now().plusDays(5), reservationTime),
                new Reservation(2L, new ClientName("브라운"), LocalDate.now().plusDays(5), reservationTime),
                new Reservation(3L, new ClientName("안나"), LocalDate.now().plusDays(5), reservationTime)
        );
        given(reservationService.getReservations()).willReturn(reservations);

        // When & Then
        mockMvc.perform(get("/reservations"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @DisplayName("예약 정보를 저장한다.")
    @Test
    void saveReservationTest() throws Exception {
        // Given
        SaveReservationRequest saveReservationRequest = new SaveReservationRequest(LocalDate.now().plusDays(5), "브라운", 1L);
        ReservationTime savedReservationTime = new ReservationTime(1L, LocalTime.now().plusHours(3));
        Reservation savedReservation = new Reservation(1L, new ClientName("브라운"), LocalDate.now().plusDays(5), savedReservationTime);
        given(reservationService.saveReservation(saveReservationRequest)).willReturn(savedReservation);

        // When & Then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveReservationRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @DisplayName("예약 정보를 삭제한다.")
    @Test
    void deleteReservationTest() throws Exception {
        // Given
        final long reservationId = 1;
        willDoNothing().given(reservationService).deleteReservation(reservationId);

        // When & Then
        mockMvc.perform(delete("/reservations/" + reservationId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 예약 정보를 삭제하려고 하면 400코드가 응답된다.")
    @Test
    void deleteNoExistReservationTest() throws Exception {
        // Given
        doThrow(new NoSuchElementException("해당 id의 예약이 존재하지 않습니다."))
                .when(reservationService)
                .deleteReservation(1L);

        // When & Then
        mockMvc.perform(delete("/reservations/1"))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("전체 예약 시간 정보를 조회한다.")
    @Test
    void getReservationTimesTest() throws Exception {
        // Given
        List<ReservationTime> reservationTimes = List.of(
                new ReservationTime(1L, LocalTime.now().plusHours(3)),
                new ReservationTime(2L, LocalTime.now().plusHours(4)),
                new ReservationTime(3L, LocalTime.now().plusHours(5))
        );
        given(reservationService.getReservationTimes()).willReturn(reservationTimes);

        // When & Then
        mockMvc.perform(get("/times"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }

    @DisplayName("예약 시간 정보를 저장한다.")
    @Test
    void saveReservationTimeTest() throws Exception {
        SaveReservationTimeRequest saveReservationTimeRequest = new SaveReservationTimeRequest(LocalTime.now().plusHours(3));
        ReservationTime savedReservationTime = new ReservationTime(1L, LocalTime.now().plusHours(3));
        given(reservationService.saveReservationTime(saveReservationTimeRequest)).willReturn(savedReservationTime);

        // When & Then
        mockMvc.perform(post("/times")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveReservationTimeRequest))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @DisplayName("존재하지 않는 예약 시간을 포함한 예약 저장 요청을 하면 400코드가 응답된다.")
    @Test
    void saveReservationWithNoExistReservationTime() throws Exception {
        // Given
        SaveReservationRequest saveReservationRequest = new SaveReservationRequest(LocalDate.now(), "브라운", 1L);
        doThrow(new NoSuchElementException("해당 id의 예약 시간이 존재하지 않습니다."))
                .when(reservationService)
                .saveReservation(saveReservationRequest);

        // When & Then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveReservationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("현재 날짜보다 이전 날짜의 예약을 저장하려고 요청하면 400코드가 응답된다.")
    @Test
    void saveReservationWithReservationDateAndTimeBeforeNow() throws Exception {
        // Given
        SaveReservationRequest saveReservationRequest = new SaveReservationRequest(LocalDate.now().minusDays(1), "브라운", 1L);
        doThrow(new IllegalArgumentException("현재 날짜보다 이전 날짜를 예약할 수 없습니다."))
                .when(reservationService)
                .saveReservation(saveReservationRequest);

        // When & Then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveReservationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("예약 시간 정보를 삭제한다.")
    @Test
    void deleteReservationTimeTest() throws Exception {
        // Given
        final long reservationTimeId = 1;
        willDoNothing().given(reservationService).deleteReservationTime(reservationTimeId);

        // When & Then
        mockMvc.perform(delete("/times/" + reservationTimeId))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @DisplayName("존재하지 않는 예약 시간 정보를 삭제하려고 하면 400코드가 응답된다.")
    @Test
    void deleteNoExistReservationTimeTest() throws Exception {
        // Given
        final long reservationTimeId = 1;
        doThrow(new NoSuchElementException("해당 id의 예약 시간이 존재하지 않습니다."))
                .when(reservationService)
                .deleteReservationTime(reservationTimeId);

        // When & Then
        mockMvc.perform(delete("/times/" + reservationTimeId))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @DisplayName("유효하지 않은 사용자 이름을 포함한 예약 저장 요청을 하면 400코드가 응답된다.")
    @Test
    void saveReservationWithInvalidName() throws Exception {
        // Given
        SaveReservationRequest saveReservationRequest = new SaveReservationRequest(LocalDate.now(), "브라운운운운운운운운운", 1L);
        doThrow(new IllegalArgumentException("예약자 이름은 1글자 이상 5글자 이하여야 합니다."))
                .when(reservationService)
                .saveReservation(saveReservationRequest);

        // When & Then
        mockMvc.perform(post("/reservations")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(saveReservationRequest))
                )
                .andDo(print())
                .andExpect(status().isBadRequest());
    }
}
