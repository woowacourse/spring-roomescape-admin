package roomescape.reservation.repository;

import static org.assertj.core.api.Assertions.assertThat;

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

@JdbcTest(properties = "application-test.properties")
@Import({ReservationRepository.class, ReservationTimeRepository.class})
class ReservationRepositoryTest {

    ReservationTime time = ReservationTime.createWithoutId(LocalTime.of(12, 0));
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ReservationTimeRepository reservationTimeRepository;

    @DisplayName("전체 예약 리스트 불러온다.")
    @Test
    void findAll() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        reservationRepository.insertReservation(reservationWithoutId);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.getFirst())
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, 12, 1));
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void insertReservation() {
        // when
        ReservationTime reservationTime = reservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        Reservation reservation = reservationRepository.insertReservation(reservationWithoutId);

        // then
        assertThat(reservation)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, 12, 1));
    }

    @DisplayName("id가 일치하는 예약을 삭제하면 true를 반환한다.")
    @Test
    void deleteReservationById_existId() {
        // given
        ReservationTime reservationTime = reservationTimeRepository.insertTime(time);
        Reservation reservationWithoutId = Reservation.createWithoutId("test", LocalDate.of(2024, 12, 1),
                reservationTime);
        Reservation reservation = reservationRepository.insertReservation(reservationWithoutId);

        // when
        boolean isDeleted = reservationRepository.deleteReservationById(reservation.getId());

        // then
        assertThat(isDeleted).isTrue();
    }

    @DisplayName("id가 존재하지 않는 예약을 삭제 요청하면 false를 반환한다.")
    @Test
    void deleteReservationById_notExistId() {
        // given
        long id = 1L;

        // when
        boolean isDeleted = reservationRepository.deleteReservationById(id);

        // then
        assertThat(isDeleted).isFalse();
    }
}