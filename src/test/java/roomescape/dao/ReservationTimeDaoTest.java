package roomescape.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

@SpringBootTest
class ReservationTimeDaoTest {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ReservationTimeDao reservationTimeDao;

    @AfterEach
    void cleanUp() {
        jdbcTemplate.execute("DELETE FROM reservation");
        jdbcTemplate.execute("DELETE FROM reservation_time");
    }

    @Test
    void 존재하는_시간() {
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES ?", "10:00");

        Optional<ReservationTime> reservationTime = reservationTimeDao.findById(1);

        assertThat(reservationTime).isPresent();
    }

    @Test
    void 존재하지_않는_시간() {
        Optional<ReservationTime> reservationTime = reservationTimeDao.findById(999L);

        assertThat(reservationTime).isEmpty();
    }

}