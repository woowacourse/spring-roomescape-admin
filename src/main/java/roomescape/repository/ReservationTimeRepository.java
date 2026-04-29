package roomescape.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

@Repository
public class ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> getAll() {
        return jdbcTemplate.query(
            "SELECT id, start_at FROM reservation",
            reservationTimeRowMapper);
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (rs, rowNum) ->
        new ReservationTime(
            rs.getLong("id"),
            rs.getTime("time").toLocalTime()
        );
}
