package roomescape.reservation.dao;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.reservation.domain.ReservationTime;

@JdbcTest
class JdbcReservationTimeDaoTest {

    private final JdbcReservationTimeDao jdbcReservationTimeDao;

    @Autowired
    private JdbcReservationTimeDaoTest(JdbcTemplate jdbcTemplate) {
        jdbcReservationTimeDao = new JdbcReservationTimeDao(jdbcTemplate);
    }


    @DisplayName("DB 시간 추가 테스트")
    @Test
    void save() {
        ReservationTime reservationTime = new ReservationTime(null, LocalTime.of(10, 0));
        ReservationTime savedReservationTime = jdbcReservationTimeDao.save(reservationTime);

        assertThat(savedReservationTime.getStartAt()).isEqualTo(LocalTime.of(10, 0));
    }

    @DisplayName("DB 모든 시간 조회 테스트")
    @Test
    void findAllReservationTimes() {
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAllReservationTimes();

        assertThat(reservationTimes).isEmpty();
    }

    @DisplayName("DB 시간 삭제 테스트")
    @Test
    void delete() {
        jdbcReservationTimeDao.delete(1L);
        List<ReservationTime> reservationTimes = jdbcReservationTimeDao.findAllReservationTimes();

        assertThat(reservationTimes).isEmpty();
    }
}
