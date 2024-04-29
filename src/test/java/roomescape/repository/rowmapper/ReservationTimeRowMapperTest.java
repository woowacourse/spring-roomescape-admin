package roomescape.repository.rowmapper;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.entity.ReservationTime;

@JdbcTest
@Import(ReservationTimeRowMapper.class)
class ReservationTimeRowMapperTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ReservationTimeRowMapper rowMapper;

    @DisplayName("ReservationTimeRowMapper 객체와 JdbcTemplate을 이용하여 객체를 매핑한다.")
    @Test
    void mapRow() {
        // given
        jdbcTemplate.update("INSERT INTO reservation_time(start_at) VALUES (?)", "10:00");

        // when
        String sql = "SELECT * FROM reservation_time";
        ReservationTime reservationTime = jdbcTemplate.queryForObject(sql, rowMapper);

        // then
        assertThat(reservationTime.getId()).isEqualTo(1);
        assertThat(reservationTime.getStartAt()).isEqualTo("10:00");
    }
}
