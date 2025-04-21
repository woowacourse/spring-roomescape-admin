package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import roomescape.domain.ReservationTime;

public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> new ReservationTime(rs.getLong("id"), rs.getTime("start_at").toLocalTime())
        );
    }
}
