package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.model.Reservation;
import roomescape.model.ReservationDateTime;

@JdbcTest
class ReservationDaoTest {

    private ReservationDao reservationDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        this.reservationDao = new ReservationDao(jdbcTemplate);
    }

    @DisplayName("저장된 모든 예약을 조회한다.")
    @Test
    void findAllReservations() {
        List<Reservation> reservations = reservationDao.findAll();

        assertThat(reservations).hasSize(0);
    }

    @DisplayName("예약을 저장한다.")
    @Test
    void insertReservation() {
        Reservation reservation = new Reservation(0L, "포스티",
                new ReservationDateTime(LocalDateTime.of(2025, 4, 23, 10, 0)));

        Long id = reservationDao.insert(reservation);

        assertThat(id).isEqualTo(1L);
    }
}
