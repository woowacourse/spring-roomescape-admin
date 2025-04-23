package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.model.Reservation;

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
}
