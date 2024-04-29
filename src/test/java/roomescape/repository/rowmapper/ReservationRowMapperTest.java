package roomescape.repository.rowmapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.Reservation;

@JdbcTest
@Import(ReservationRowMapper.class)
class ReservationRowMapperTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationRowMapper rowMapper;

    @DisplayName("ReservationRowMapper 객체와 JdbcTemplate을 이용하여 객체를 매핑한다.")
    @Test
    void mapRow() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "10:00");
        jdbcTemplate.update("INSERT INTO reservation(name, date, time_id) VALUES (?, ?, ?)",
                "상돌", "2024-04-25", 1);

        // when
        String sql = """
                SELECT 
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at AS time_value
                FROM reservation AS r
                INNER JOIN reservation_time as t
                ON r.time_id = t.id
                """;

        Reservation reservation = jdbcTemplate.queryForObject(sql, rowMapper);

        // then
        assertThat(reservation.getId()).isEqualTo(1);
        assertThat(reservation.getName()).isEqualTo("상돌");
        assertThat(reservation.getDate()).isEqualTo("2024-04-25");
        assertThat(reservation.getTime().getId()).isEqualTo(1L);
        assertThat(reservation.getTime().getStartAt()).isEqualTo("10:00");
    }
}
