package roomescape.db;

import java.time.LocalTime;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@JdbcTest
class ReservationTimeDaoTest {
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Test
    @DisplayName("예약시간을 조회할 수 있다")
    void read() {
        final ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate);

        Assertions.assertThat(reservationTimeDao.findAll()).isEmpty();
    }

    @Test
    @DisplayName("예약시간을 저장할 수 있다")
    void save() {
        final ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate);

        reservationTimeDao.save(new ReservationTime(LocalTime.now()));
        Assertions.assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

    @Test
    @DisplayName("예약시간을 삭제할 수 있다")
    void delete() {
        final ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        ReservationTime saved = reservationTimeDao.save(new ReservationTime(LocalTime.now()));

        reservationTimeDao.deleteById(saved.getId());

        Assertions.assertThat(reservationTimeDao.findAll()).isEmpty();
    }
}
