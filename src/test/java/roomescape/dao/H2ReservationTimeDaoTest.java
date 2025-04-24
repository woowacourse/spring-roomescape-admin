package roomescape.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class H2ReservationTimeDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ReservationTimeDao reservationTimeDao;

    @BeforeEach
    void setUp() {
        reservationTimeDao = new H2ReservationTimeDao(jdbcTemplate);
    }

    @Test
    void 모든_예약시간을_조회한다() {
        assertThat(reservationTimeDao.findAll()).hasSize(1);
    }

    @Test
    void 특정_예약시간을_조회한다() {
        ReservationTime reservationTime = ReservationTime.of(LocalTime.of(10, 0));
        ReservationTime savedReservationTime = reservationTimeDao.insert(reservationTime);
        assertThat(reservationTimeDao.findById(savedReservationTime.getId())).isEqualTo(savedReservationTime);
    }

    @Test
    void 예약시간을_추가하면_추가한_예약시간을_반환한다() {
        ReservationTime reservationTime = ReservationTime.of(LocalTime.of(10, 0));
        assertThat(reservationTimeDao.insert(reservationTime)).isNotNull();
    }

    @Test
    void 특정_예약시간을_취소하면_true를_반환한다() {
        ReservationTime reservationTime = ReservationTime.of(LocalTime.of(11, 0));
        ReservationTime savedReservationTime = reservationTimeDao.insert(reservationTime);
        assertThat(reservationTimeDao.deleteById(savedReservationTime.getId())).isTrue();
    }

    @Test
    void 특정_예약시간을_취소했을때_예약이_없으면_false를_반환한다() {
        assertThat(reservationTimeDao.deleteById(2L)).isFalse();
    }
}
