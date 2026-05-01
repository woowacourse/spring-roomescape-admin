package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.List;

@Repository
public class TimeQueryingRepository {
    private JdbcTemplate jdbcTemplate;

    public TimeQueryingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    };

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    public ReservationTime findById(Long id) {
        String sql = "select id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
    }
}
