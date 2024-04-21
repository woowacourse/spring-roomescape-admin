package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

import java.util.List;

@Repository
public class H2ReservationTimeRepositoryImpl implements ReservationTimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public H2ReservationTimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "select * from reservation_time";

        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()));
    }
}
