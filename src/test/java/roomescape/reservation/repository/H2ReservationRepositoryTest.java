package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;
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
import roomescape.reservation.repository.h2.H2ReservationRepository;
import roomescape.reservation.repository.h2.H2ReservationTimeRepository;

@JdbcTest(properties = "application-test.properties")
@Import({H2ReservationRepository.class, H2ReservationTimeRepository.class})
class H2ReservationRepositoryTest {

    ReservationTime time = ReservationTime.createWithoutId(LocalTime.of(12, 0));
    @Autowired
    private H2ReservationRepository h2ReservationRepository;
    @Autowired
    private H2ReservationTimeRepository h2ReservationTimeRepository;

    @DisplayName("전체 예약 리스트 불러온다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = h2ReservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        h2ReservationRepository.insertReservation(reservationWithoutId);

        // when
        List<Reservation> reservations = h2ReservationRepository.findAll();

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
        ReservationTime reservationTime = h2ReservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        Reservation reservation = h2ReservationRepository.insertReservation(reservationWithoutId);

        // then
        assertThat(reservation.getName()).isEqualTo("test");
        assertThat(reservation.getDate()).isEqualTo(LocalDate.of(2024, 12, 1));
    }

    @DisplayName("id가 일치하는 예약을 삭제하면 true를 반환한다.")
    @Test
    void deleteReservationById_existId() {
        // given
        ReservationTime reservationTime = h2ReservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        Reservation reservation = h2ReservationRepository.insertReservation(reservationWithoutId);

        // when
        boolean isDeleted = h2ReservationRepository.deleteReservationById(reservation.getId());

        // then
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("id가 존재하지 않는 예약을 삭제 요청하면 false를 반환한다.")
    @Test
    void deleteReservationById_notExistId() {
        // given
        long id = 1L;

        // when
        boolean isDeleted = h2ReservationRepository.deleteReservationById(id);

        // then
        assertThat(isDeleted).isFalse();
    }
}