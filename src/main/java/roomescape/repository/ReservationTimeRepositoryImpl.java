package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime insert(final LocalTime startAt) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", startAt);
        long id = simpleJdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationTime(id, startAt);
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNumber) -> {
            long id = resultSet.getLong("id");
            LocalTime startAt = LocalTime.parse(resultSet.getString("start_at"));
            return new ReservationTime(id, startAt);
        });
    }

    @Override
    public void delete(final long id) {
        ReservationTime reservationTime = findById(id);

        final String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ReservationTime findById(final long timeId) {
        final String sql = "select * from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, (resultSet, rowNumber) -> {
            LocalTime startTime = LocalTime.parse(resultSet.getString("start_at"));
            return new ReservationTime(timeId, startTime);
        }, timeId);
    }
}
