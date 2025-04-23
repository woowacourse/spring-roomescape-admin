package roomescape.reservation.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.DirtiesContext;
import roomescape.reservation.entity.Reservation;
import roomescape.reservation.entity.ReservationTime;
import roomescape.reservation.exception.EntityNotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@JdbcTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ReservationDaoImplTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    private ReservationDaoImpl reservationDao;

    @BeforeEach
    void setUp() {
        reservationDao = new ReservationDaoImpl(jdbcTemplate);
    }

    @Test
    void 예약을_저장한다() {
        // given
        LocalTime time = LocalTime.of(10, 0);
        jdbcTemplate.update(
            "INSERT INTO reservation_time (start_at) VALUES (:startAt)",
            new MapSqlParameterSource("startAt", time)
        );

        Reservation reservation = new Reservation(
            "drago",
            LocalDate.of(2025, 5, 1),
            new ReservationTime(1L, time)
        );

        // when
        Reservation savedReservation = reservationDao.save(reservation);

        // then
        Reservation expected = new Reservation(
            1L,
            "drago",
            LocalDate.of(2025, 5, 1),
            new ReservationTime(1L, LocalTime.of(10, 0))
        );
        assertThat(savedReservation).isEqualTo(expected);
    }

    @Test
    void 모든_예약을_조회한다() {
        // given
        LocalTime time = LocalTime.of(10, 0);
        jdbcTemplate.update(
            "INSERT INTO reservation_time (start_at) VALUES (:startAt)",
            new MapSqlParameterSource("startAt", time)
        );

        Reservation drago = reservationDao.save(new Reservation("drago",
            LocalDate.of(2025, 5, 1),
            new ReservationTime(1L, time)));

        Reservation cookie = reservationDao.save(new Reservation("cookie",
            LocalDate.of(2025, 5, 2),
            new ReservationTime(1L, time)));

        // when
        List<Reservation> all = reservationDao.findAll();

        // then
        assertThat(all).containsExactly(drago, cookie);
    }

    @Test
    void 없는_예약을_삭제하면_예외가_발생한다() {
        // when & then
        assertThatThrownBy(() -> reservationDao.deleteById(9L))
            .isInstanceOf(EntityNotFoundException.class)
            .hasMessage("삭제할 예약정보가 없습니다.");
    }

    @Test
    void 예약을_삭제한다() {
        // given
        LocalTime time = LocalTime.of(10, 0);
        jdbcTemplate.update(
            "INSERT INTO reservation_time (start_at) VALUES (:startAt)",
            new MapSqlParameterSource("startAt", time)
        );

        Reservation drago = reservationDao.save(new Reservation("drago",
            LocalDate.of(2025, 5, 1),
            new ReservationTime(1L, time)));

        // when
        reservationDao.deleteById(drago.getId());

        // then
        List<Reservation> all = reservationDao.findAll();
        assertThat(all.isEmpty()).isTrue();
    }
}