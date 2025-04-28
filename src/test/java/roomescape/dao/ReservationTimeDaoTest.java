package roomescape.dao;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import roomescape.common.DbTest;
import roomescape.domain.ReservationTime;

class ReservationTimeDaoTest extends DbTest {
    private ReservationTimeDao reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    private final LocalDateTime 오늘_이후_25년_4월_22일_10시 = LocalDateTime.of(2025, 4, 22, 10, 0);

    @BeforeEach
    void setup() {
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
    }

    @Test
    void 예약시간을_생성한다() {
        reservationTimeDao.save(오늘_이후_25년_4월_22일_10시.toLocalTime());
        Long count = getReservationCount();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약_시간을_삭제한다() {
        reservationTimeDao.save(오늘_이후_25년_4월_22일_10시.toLocalTime());

        reservationTimeDao.deleteById(1L);

        Long count = getReservationCount();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void 모든_예약을_조회한다() {
        reservationTimeDao.save(오늘_이후_25년_4월_22일_10시.toLocalTime());

        SoftAssertions softly = new SoftAssertions();

        List<ReservationTime> reservationTimes = reservationTimeDao.findAll();
        softly.assertThat(reservationTimes).hasSize(1);

        ReservationTime reservationTime = reservationTimes.getFirst();
        softly.assertThat(reservationTime.id()).isEqualTo(1L);
        softly.assertThat(reservationTime.time()).isEqualTo(오늘_이후_25년_4월_22일_10시.toLocalTime());
        softly.assertAll();
    }

    @Test
    @DisplayName("예약이 존재하는 경우")
    void 예약을_단일_조회한다1() {
        reservationTimeDao.save(오늘_이후_25년_4월_22일_10시.toLocalTime());
        assertThat(reservationTimeDao.findById(1L)).isPresent();
    }

    @Test
    @DisplayName("예약이 존재하지 않는 경우")
    void 예약을_단일_조회한다2() {
        assertThat(reservationTimeDao.findById(1L)).isNotPresent();
    }

    private Long getReservationCount() {
        Long count = jdbcTemplate.queryForObject("select count(*) from reservation_time", Long.class);
        return count;
    }
}
