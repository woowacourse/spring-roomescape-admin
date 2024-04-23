package roomescape.domain;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeRepository {
    private JdbcTemplate jdbcTemplate;

    public ReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", reservationTimeRowMapper());
    }

    public ReservationTime findById(long id) {
        return jdbcTemplate.queryForObject("SELECT id, start_at FROM reservation_time WHERE id = ?",
                reservationTimeRowMapper(), id);
    }

    private RowMapper<ReservationTime> reservationTimeRowMapper() {
        return (resultSet, rowNum) -> {
            ReservationTime reservationTime = new ReservationTime(
                    resultSet.getLong("id"),
                    resultSet.getString("start_at")
            );
            return reservationTime;
        };
    }
}
