package roomescape.dao;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import roomescape.common.Constant;
import roomescape.common.DbTest;
import roomescape.config.JdbcTemplateInitializer;
import roomescape.domain.Reservation;
import roomescape.domain.ReservationDateTime;
import roomescape.domain.ReservationTime;
import roomescape.fixture.ReservationDateFixture;
import roomescape.fixture.ReservationNameFixture;
import roomescape.fixture.ReservationTimeDbFixture;

class ReservationDaoTest extends DbTest {
    private ReservationDao reservationDao = new ReservationDao(jdbcTemplate);
    private final ReservationTimeDbFixture reservationTimeDbFixture = new ReservationTimeDbFixture(
            JdbcTemplateInitializer.getJdbcTemplate()
    );
    private ReservationTime reservationTime ;
    private final LocalDateTime 오늘_이후_25년_4월_22일_10시 = LocalDateTime.of(2025, 4, 22, 10, 0);

    @BeforeEach
    void setup() {
        reservationTime = reservationTimeDbFixture.예약_시간_생성_10시();
    }

    @Test
    void 예약을_생성한다() {
        reservationDao.save(
                ReservationNameFixture.한스,
                new ReservationDateTime(
                        ReservationDateFixture.예약일_25_4_22,
                        reservationTime,
                        Constant.FIXED_CLOCK
                )
        );
        Long count = getReservationCount();
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약을_삭제한다() {
        reservationDao.save(
                ReservationNameFixture.한스,
                new ReservationDateTime(
                        ReservationDateFixture.예약일_25_4_22,
                        reservationTime,
                        Constant.FIXED_CLOCK
                )
        );

        reservationDao.deleteById(reservationTime.id());

        Long count = getReservationCount();
        assertThat(count).isEqualTo(0);
    }

    @Test
    void 예약을_조회한다() {
        reservationDao.save(
                ReservationNameFixture.한스,
                new ReservationDateTime(
                        ReservationDateFixture.예약일_25_4_22,
                        reservationTime,
                        Constant.FIXED_CLOCK
                )
        );

        SoftAssertions softly = new SoftAssertions();

        List<Reservation> reservations = reservationDao.findAll();
        softly.assertThat(reservations).hasSize(1);

        Reservation reservation = reservations.getFirst();
        softly.assertThat(reservation.date()).isEqualTo(오늘_이후_25년_4월_22일_10시.toLocalDate());
        softly.assertThat(reservation.id()).isEqualTo(1L);
        softly.assertThat(reservation.name()).isEqualTo("한스");
        softly.assertThat(reservation.time()).isEqualTo(오늘_이후_25년_4월_22일_10시.toLocalTime());
        softly.assertAll();
    }

    private Long getReservationCount() {
        Long count = jdbcTemplate.queryForObject("select count(*) from reservation", Long.class);
        return count;
    }
}
