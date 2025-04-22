package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.entity.ReservationTime;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt());
        Number savedId = simpleJdbcInsert.execute(parameters);

        return ReservationTime.of(savedId.longValue(), reservationTime.getStartAt());
    }

    @Override
    public boolean deleteById(final Long id) {
        return false;
    }
}
