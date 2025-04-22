package roomescape.domain.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.ReservationTime;
import roomescape.domain.ReservationTimes;
import roomescape.domain.Reservations;
import roomescape.domain.dao.FakeReservationDaoImpl;
import roomescape.dto.ReservationRequestDto;
import roomescape.service.ReservationService;


public class ReservationServiceTest {

    private ReservationTimes reservationTimes;
    private Reservations reservations;
    private ReservationService reservationService;

    @BeforeEach
    void init() {
        reservationTimes = new ReservationTimes();
        reservations = new Reservations();
        reservationService = new ReservationService(
            new FakeReservationDaoImpl(), reservations, reservationTimes);
    }

    @DisplayName("ReservationRequestDto가 주어졌을 때, Fake 객체 및 캐싱 DB에 정상적으로 저장되어야 한다.")
    @Test
    void given_reservation_request_dto_then_save() {
        //given
        reservationTimes.addReservationTime(new ReservationTime(1L, LocalTime.of(10, 0)));
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(
            "james", "2025-05-12", 1L);

        //when
        reservationService.saveReservation(reservationRequestDto);

        //then
        assertThat(reservationService.getAllReservations().size()).isEqualTo(1);
        assertThat(reservations.getReservations().size()).isEqualTo(1);
    }

    @DisplayName("여러 번 Reservation을 저장할 때, 성공적으로 Fake객체 및 캐싱 DB에 저장되고, 읽어올 수 있어야 한다.")
    @Test
    void given_multiple_reservation_request_dto_then_all_save() {
        //given
        reservationTimes.addReservationTime(new ReservationTime(1L, LocalTime.of(10, 0)));
        reservationTimes.addReservationTime(new ReservationTime(2L, LocalTime.of(12, 0)));

        ReservationRequestDto reservationRequestDto1 = new ReservationRequestDto(
            "james", "2025-05-12", 1L);
        ReservationRequestDto reservationRequestDto2 = new ReservationRequestDto(
            "james", "2025-05-13", 2L);
        ReservationRequestDto reservationRequestDto3 = new ReservationRequestDto(
            "james", "2025-05-14", 2L);

        //when
        reservationService.saveReservation(reservationRequestDto1);
        reservationService.saveReservation(reservationRequestDto2);
        reservationService.saveReservation(reservationRequestDto3);

        //then
        assertThat(reservationService.getAllReservations().size()).isEqualTo(3);
        assertThat(reservations.getReservations().size()).isEqualTo(3);
    }

    @DisplayName("reservationId가 주어졌을 떄, Fake 객체 및 캐싱DB에서 삭제되어야 한다.")
    @Test
    void given_reservation_id_then_remove_db() {
        //given
        reservationTimes.addReservationTime(new ReservationTime(1L, LocalTime.of(10, 0)));
        ReservationRequestDto reservationRequestDto = new ReservationRequestDto(
            "james", "2025-05-12", 1L);
        reservationService.saveReservation(reservationRequestDto);

        //when
        reservationService.deleteReservation(1L);

        //then
        assertThat(reservationService.getAllReservations().size()).isEqualTo(0);
        assertThat(reservations.getReservations().size()).isEqualTo(0);
    }
}
