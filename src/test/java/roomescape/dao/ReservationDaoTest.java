package roomescape.dao;

import static org.assertj.core.api.Assertions.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
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
class ReservationDaoTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ReservationDao reservationDao;
    private final LocalDateTime now = LocalDateTime.of(2025, 4, 22, 10, 0);

    private ReservationTime reservationTime;

    @BeforeEach
    void setup() {
        reservationDao = new ReservationDao(jdbcTemplate);
        String sql = "insert into reservation_time(start_at) values(?)";
        LocalTime startAt = LocalTime.of(10, 0);
        jdbcTemplate.update(sql, startAt);
        reservationTime = new ReservationTime(1L, startAt);
    }

    @Test
    void 예약을_생성한다() {
        reservationDao.createReservation(
                new ReservationName("한스"),
                new ReservationDateTime(
                        new ReservationDate(now.toLocalDate()),
                        reservationTime,
                        Constant.FIXED_CLOCK
                )
        );
        Long count = jdbcTemplate.queryForObject("select count(*) from reservation", Long.class);
        assertThat(count).isEqualTo(1);
    }

    @Test
    void 예약을_삭제한다() {
        reservationDao.createReservation(
                new ReservationName("한스"),
                new ReservationDateTime(
                        new ReservationDate(now.toLocalDate()),
                        reservationTime,
                        Constant.FIXED_CLOCK
                )
        );
        reservationDao.deleteReservationById(reservationTime.id());
        Long count = jdbcTemplate.queryForObject("select count(*) from reservation", Long.class);
        assertThat(count).isEqualTo(0);
    }

    @Test
    void 예약을_조회한다() {
        reservationDao.createReservation(
                new ReservationName("한스"),
                new ReservationDateTime(
                        new ReservationDate(now.toLocalDate()),
                        reservationTime,
                        Constant.FIXED_CLOCK
                )
        );
        List<Reservation> reservations = reservationDao.getReservations();
        SoftAssertions softly = new SoftAssertions();
        softly.assertThat(reservations).hasSize(1);
        Reservation reservation = reservations.getFirst();
        softly.assertThat(reservation.date()).isEqualTo(now.toLocalDate());
        softly.assertThat(reservation.id()).isEqualTo(1L);
        softly.assertThat(reservation.name()).isEqualTo("한스");
        softly.assertThat(reservation.time()).isEqualTo(now.toLocalTime());
        softly.assertAll();
    }
}
