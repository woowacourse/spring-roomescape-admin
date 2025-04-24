package roomescape.repositiory;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.dto.ReservationRequestDto;

@JdbcTest
class ReservationH2RepositoryTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @DisplayName("예약 객체를 추가한다")
    @Test
    void add() {
        // given
        ReservationRepository reservationRepository = new ReservationH2Repository(jdbcTemplate);
        ReservationRequestDto reservation = new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now());

        // when
        Long id = reservationRepository.add(reservation);

        // then
        Assertions.assertThat(id).isEqualTo(reservationRepository.findById(id).getId());
        Assertions.assertThat(reservationRepository.findAll()).hasSize(1);
    }

    @DisplayName("모든 예약 객체를 반환한다")
    @Test
    void findAll() {
        // given
        ReservationRepository reservationRepository = new ReservationH2Repository(jdbcTemplate);
        ReservationRequestDto reservation = new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now());
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
        ReservationRepository reservationRepository = new ReservationH2Repository(jdbcTemplate);
        ReservationRequestDto reservation = new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now());
        Long id = reservationRepository.add(reservation);

        // when
        Reservation findReservation = reservationRepository.findById(id);

        // then
        Assertions.assertThat(findReservation.getId()).isEqualTo(id);
    }

    @DisplayName("예약 객체를 삭제한다")
    @Test
    void delete() {
        // given
        ReservationRepository reservationRepository = new ReservationH2Repository(jdbcTemplate);
        ReservationRequestDto reservation = new ReservationRequestDto("예약자", LocalDate.now(), LocalTime.now());
        Long id = reservationRepository.add(reservation);

        // when
        reservationRepository.delete(id);

        // then
        Assertions.assertThat(reservationRepository.findAll()).isEmpty();
    }
}