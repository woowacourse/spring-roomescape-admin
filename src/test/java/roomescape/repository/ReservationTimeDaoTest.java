package roomescape.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;

import roomescape.model.ReservationTime;

@JdbcTest
@Import(ReservationTimeDao.class)
class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @DisplayName("예약을 저장한다.")
    @Test
    void insertReservationTime() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));

        ReservationTime insertedReservationTime = reservationTimeDao.insert(reservationTime);

        assertThat(insertedReservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("모든 예약을 조회한다.")
    @Test
    void findAllReservationTime() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));
        reservationTimeDao.insert(reservationTime);

        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

}
