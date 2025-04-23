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
import roomescape.reservation.model.ReservationDetails;

@JdbcTest(properties = "application-test.properties")
@Import(ReservationRepository.class)
class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    ReservationDetails reservationDetails = new ReservationDetails("test", LocalDate.of(2024, 12, 1),
            LocalTime.of(12, 1));

    @DisplayName("전체 예약 리스트 불러온다.")
    @Test
    void findAll() {
        // given
        reservationRepository.insertReservation(reservationDetails);

        // when
        List<Reservation> reservations = reservationRepository.findAll();

        // then
        assertThat(reservations).hasSize(1);
        assertThat(reservations.getFirst())
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, 12, 1))
                .hasFieldOrPropertyWithValue("time", LocalTime.of(12, 1));
    }

    @DisplayName("예약을 추가한다.")
    @Test
    void insertReservation() {
        // when
        Reservation reservation = reservationRepository.insertReservation(reservationDetails);

        // then
        assertThat(reservation)
                .hasFieldOrPropertyWithValue("name", "test")
                .hasFieldOrPropertyWithValue("date", LocalDate.of(2024, 12, 1))
                .hasFieldOrPropertyWithValue("time", LocalTime.of(12, 1));
    }

    @DisplayName("id가 일치하는 예약을 삭제하면 true를 반환한다.")
    @Test
    void deleteReservationById_existId() {
        // given
        Reservation reservation = reservationRepository.insertReservation(reservationDetails);

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