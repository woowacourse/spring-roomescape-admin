package roomescape.reservation.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import roomescape.reservation.entity.ReservationTime;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ReservationReservationTimeInMemoryRepositoryTest {

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;
    ReservationTimeInMemoryRepository repository;

    @BeforeEach
    void setUp() {
        this.repository = new ReservationTimeInMemoryRepository(jdbcTemplate);
    }

    @Test
    void 예약시간을_저장한다() {
        // given
        ReservationTime reservationTime = new ReservationTime(LocalTime.of(10, 0));

        // when
        ReservationTime savedReservationTime = repository.save(reservationTime);

        // then
        ReservationTime expected = jdbcTemplate.queryForObject(
            "SELECT * FROM reservation_time",
            new MapSqlParameterSource(),
            (rs, rownum) -> {
                return new ReservationTime(
                    rs.getLong("id"),
                    rs.getObject("start_at", LocalTime.class)
                );
            });
        assertThat(savedReservationTime).isEqualTo(expected);
    }
}