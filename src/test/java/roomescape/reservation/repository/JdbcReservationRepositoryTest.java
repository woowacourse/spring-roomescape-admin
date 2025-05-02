package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import roomescape.reservation.model.Reservation;
import roomescape.reservation.model.ReservationTime;
import roomescape.reservation.repository.jdbc.JdbcReservationRepository;
import roomescape.reservation.repository.jdbc.JdbcReservationTimeRepository;

@JdbcTest(properties = "application-test.properties")
@Import({JdbcReservationRepository.class, JdbcReservationTimeRepository.class})
class JdbcReservationRepositoryTest {

    ReservationTime time = ReservationTime.createWithoutId(LocalTime.of(12, 0));
    @Autowired
    private JdbcReservationRepository jdbcReservationRepository;
    @Autowired
    private JdbcReservationTimeRepository jdbcReservationTimeRepository;

    @DisplayName("전체 예약 리스트 불러온다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        jdbcReservationRepository.insertReservation(reservationWithoutId);

        // when
        List<Reservation> reservations = jdbcReservationRepository.findAll();

        // then
        Reservation reservation = reservations.getFirst();
        assertAll(
                () -> assertThat(reservations).hasSize(1),
                () -> assertThat(reservation.getName()).isEqualTo("test"),
                () -> assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2024, 12, 1))
        );
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void insertReservation() {
        // when
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        Reservation reservation = jdbcReservationRepository.insertReservation(reservationWithoutId);

        // then
        assertThat(reservation.getName()).isEqualTo("test");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2024, 12, 1));
    }

    @DisplayName("id가 일치하는 예약을 삭제한다")
    @Test
    void deleteReservationById_existId() {
        // given
        ReservationTime reservationTime = jdbcReservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        Reservation reservation = jdbcReservationRepository.insertReservation(reservationWithoutId);

        // when, then
        assertThatCode(() -> jdbcReservationRepository.deleteReservationById(reservation.getId()))
                .doesNotThrowAnyException();
    }
}