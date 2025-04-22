package roomescape.dao;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.common.Constant;
import roomescape.config.TestClockConfig;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDate;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationName;
import roomescape.domain.ReservationTime;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Import(TestClockConfig.class)
class ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationTimeDao reservationTimeDao;
    private final LocalDateTime now = LocalDateTime.of(2025, 4, 22, 10, 0);

    @BeforeEach
    void setup() {
        reservationTimeDao = new ReservationTimeDao(jdbcTemplate);
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY FALSE");
        jdbcTemplate.update("TRUNCATE TABLE reservation");
        jdbcTemplate.update("TRUNCATE TABLE reservation_time");
        jdbcTemplate.update("ALTER TABLE reservation ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("ALTER TABLE reservation_time ALTER COLUMN id RESTART WITH 1");
        jdbcTemplate.update("SET REFERENTIAL_INTEGRITY TRUE");
    }

    @Test
    void 예약시간을_생성한다() {
        reservationTimeDao.createTime(now.toLocalTime());
        Long count = jdbcTemplate.queryForObject("select count(*) from reservation_time", Long.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약_시간을_삭제한다() {
        reservationTimeDao.createTime(now.toLocalTime());
        reservationTimeDao.deleteTimeById(1L);
        Long count = jdbcTemplate.queryForObject("select count(*) from reservation", Long.class);
        assertThat(count).isEqualTo(0);
    }

    @Test
    void 모든_예약을_조회한다() {
        reservationTimeDao.createTime(now.toLocalTime());
        List<ReservationTime> reservationTimes = reservationTimeDao.findAllTimes();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(reservationTimes).hasSize(1);
        ReservationTime reservationTime = reservationTimes.getFirst();
        softly.assertThat(reservationTime.id()).isEqualTo(1L);
        softly.assertThat(reservationTime.time()).isEqualTo(now.toLocalTime());
        softly.assertAll();
    }

    @Test
    @DisplayName("예약이 존재하는 경우")
    void 예약을_단일_조회한다1() {
        reservationTimeDao.createTime(now.toLocalTime());
        assertThat(reservationTimeDao.findTime(1L)).isPresent();
    }

    @Test
    @DisplayName("예약이 존재하지 않는 경우")
    void 예약을_단일_조회한다2() {
        assertThat(reservationTimeDao.findTime(1L)).isNotPresent();
    }
}
