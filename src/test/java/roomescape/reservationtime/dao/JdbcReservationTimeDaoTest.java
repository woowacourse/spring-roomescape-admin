package roomescape.reservationtime.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservationtime.ReservationTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class JdbcReservationTimeDaoTest {

    @Autowired
    private ReservationTimeDao reservationTimeDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        jdbcTemplate.update("delete from reservation_time");
    }

    @Test
    void 예약_시간을_추가할_수_있다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        // when
        reservationTimeDao.create(reservationTime);
        List<ReservationTime> reservationTimeDaoAll = reservationTimeDao.findAll();

        // then
        assertThat(reservationTimeDaoAll.size()).isEqualTo(1);
    }

    @Test
    void 예약_시간을_조회할_수_있다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        // when
        reservationTimeDao.create(reservationTime);
        List<ReservationTime> reservationTimeDaoAll = reservationTimeDao.findAll();

        // then
        assertThat(reservationTimeDaoAll.getFirst().getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @Test
    void 예약_시간을_삭제할_수_있다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));
        Long id = reservationTimeDao.create(reservationTime);
        int beforeSize = reservationTimeDao.findAll().size();

        // when
        reservationTimeDao.delete(id);
        int afterSize = reservationTimeDao.findAll().size();

        // then
        assertThat(beforeSize).isEqualTo(1);
        assertThat(afterSize).isEqualTo(0);
    }
}
