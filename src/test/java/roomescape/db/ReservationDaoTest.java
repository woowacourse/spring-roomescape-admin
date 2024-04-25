package roomescape.db;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationDaoTest {
    @Autowired
    JdbcTemplate jdbcTemplate;
    ReservationTimeDao reservationTimeDao;
    ReservationTime saved;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        final ReservationTime reservationTime = ReservationTime.from(LocalTime.now());
        saved = reservationTimeDao.save(reservationTime);
    }


    @Test
    @DisplayName("예약을 조회할 수 있다")
    void read() {
        final ReservationDao reservationDao = new ReservationDao(jdbcTemplate);

        Assertions.assertThat(reservationDao.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약을 저장할 수 있다")
    void save() {
        final ReservationDao reservationDao = new ReservationDao(jdbcTemplate);

        reservationDao.save(Reservation.from("qwe", LocalDate.now(), reservationTimeDao.findById(saved.getId()).get()));
        Assertions.assertThat(reservationDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약을 삭제할 수 있다")
    void delete() {
        final ReservationDao reservationDao = new ReservationDao(jdbcTemplate);
        reservationDao.save(Reservation.from("qwe", LocalDate.now(), reservationTimeDao.findById(saved.getId()).get()));

        reservationDao.deleteById(saved.getId());

        Assertions.assertThat(reservationDao.findAll()).isEmpty();
    }
}
