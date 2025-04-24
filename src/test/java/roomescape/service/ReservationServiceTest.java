package roomescape.service;

import java.time.LocalDate;
import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;
import roomescape.repositiory.ReservationH2Repository;

class ReservationServiceTest {

    @DisplayName("예약한다")
    @Test
    void add() {
        // given
        ReservationH2Repository reservationRepository = new ReservationH2Repository(new JdbcTemplate());
        ReservationService reservationService = new ReservationService(reservationRepository);

        // when
        Reservation addedReservation = reservationService.add(
                new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now()));

        // then
        Assertions.assertThat(addedReservation).isNotNull();
    }

    @DisplayName("예약을 취소한다")
    @Test
    void delete() {
        // given
        ReservationH2Repository reservationRepository = new ReservationH2Repository(new JdbcTemplate());
        ReservationService reservationService = new ReservationService(reservationRepository);
        Reservation addedReservation = reservationService.add(
                new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now()));

        // when
        reservationService.delete(addedReservation.getId());

        // then
        Assertions.assertThat(reservationService.readAll()).isEmpty();
    }

    @DisplayName("예약 목록을 불러온다")
    @Test
    void readAll() {
        // given
        ReservationH2Repository reservationRepository = new ReservationH2Repository(new JdbcTemplate());
        ReservationService reservationService = new ReservationService(reservationRepository);
        Reservation addedReservation = reservationService.add(
                new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now()));

        // when
        int firstReadSize = reservationService.readAll().size();
        reservationService.delete(addedReservation.getId());
        int secondReadSize = reservationService.readAll().size();

        // then
        Assertions.assertThat(firstReadSize).isEqualTo(1);
        Assertions.assertThat(secondReadSize).isEqualTo(0);
    }
}