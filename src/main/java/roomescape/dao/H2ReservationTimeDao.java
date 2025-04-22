package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;
import java.util.List;

@Repository
public class H2ReservationTimeDao implements ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;

    public H2ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> ReservationTime.of(
                        resultSet.getLong("id"),
                        resultSet.getObject("start_at", LocalTime.class)));
    }

    @Override
    public ReservationTime insert(final ReservationTime reservationTime) {
        return null;
    }

    @Override
    public boolean deleteById(final Long id) {
        return false;
    }
}
