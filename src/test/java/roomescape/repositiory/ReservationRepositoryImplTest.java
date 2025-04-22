package roomescape.repositiory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.domain.Reservation;

class ReservationRepositoryImplTest {

    @DisplayName("예약 객체를 추가한다")
    @Test
    void add() {
        // given
        ReservationRepository reservationRepository = new ReservationRepositoryImpl();
        Reservation reservation = Reservation.of("예약자", LocalDate.now(), LocalTime.now());

        // when
        Reservation addedReservation = reservationRepository.add(reservation);

        // then
        Assertions.assertThat(addedReservation).isEqualTo(reservation);
        Assertions.assertThat(reservationRepository.findAll()).hasSize(1);
    }

    @DisplayName("모든 예약 객체를 반환한다")
    @Test
    void findAll() {
        // given
        ReservationRepository reservationRepository = new ReservationRepositoryImpl();
        Reservation reservation = Reservation.of("예약자", LocalDate.now(), LocalTime.now());
        reservationRepository.add(reservation);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        Assertions.assertThat(reservations).hasSize(1);
    }

    @DisplayName("아이디로 예약 객체를 찾아 반환한다")
    @Test
    void findById() {
        // given
        ReservationRepository reservationRepository = new ReservationRepositoryImpl();
        Reservation reservation = Reservation.of("예약자", LocalDate.now(), LocalTime.now());
        reservationRepository.add(reservation);

        // when
        Reservation findReservation = reservationRepository.findById(reservation.getId());

        // then
        Assertions.assertThat(findReservation).isEqualTo(reservation);
    }

    @DisplayName("예약 객체를 삭제한다")
    @Test
    void delete() {
        // given
        ReservationRepository reservationRepository = new ReservationRepositoryImpl();
        Reservation reservation = Reservation.of("예약자", LocalDate.now(), LocalTime.now());
        reservationRepository.add(reservation);

        // when
        reservationRepository.delete(reservation);

        // then
        Assertions.assertThat(reservationRepository.findAll()).isEmpty();
    }
}