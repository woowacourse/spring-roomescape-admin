package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.model.Reservation;
import roomescape.model.ReservationTime;
import roomescape.model.exception.ReservationNotFoundException;

@JdbcTest
@Import({ReservationDao.class, ReservationTimeDao.class})
class ReservationDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationDao reservationDao;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM reservation");
        jdbcTemplate.update("DELETE FROM reservation_time");
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (1, '10:00')");
    }

    @DisplayName("저장된 모든 예약을 조회한다.")
    @Test
    void findAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).isEmpty();
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void insertReservation() {
        Reservation reservation = new Reservation(0L, "포스티",
                LocalDate.of(2025, 4, 23), new ReservationTime(1L, LocalTime.of(10, 0)));

        reservationDao.insert(reservation);

        assertThat(reservationDao.findAll()).hasSize(1);
    }

    @DisplayName("예약 번호와 일치하는 예약을 삭제한다.")
    @Test
    void deleteReservationById() {
        Reservation reservation = new Reservation(0L, "포스티",
                LocalDate.of(2025, 4, 23), new ReservationTime(1L, LocalTime.of(10, 0)));
        Reservation insertedReservation = reservationDao.insert(reservation);

        reservationDao.deleteById(insertedReservation.getId());

        assertThat(reservationDao.findAll()).isEmpty();
    }

    @DisplayName("존재하지 않는 예약은 삭제할 수 없다.")
    @Test
    void deleteReservationByNonExistsId() {
        assertThatThrownBy(() -> reservationDao.deleteById(1L))
                .isInstanceOf(ReservationNotFoundException.class);
    }
}
