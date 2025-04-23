package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeJdbcRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public ReservationTimeJdbcRepository(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(final ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt().toString());
        final long id = jdbcInsert.executeAndReturnKey(parameters).longValue();
        return new ReservationTime(id, reservationTime.getStartAt());
    }

    @Override
    public List<ReservationTime> findAll() {
        final String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> ReservationTime.of(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                )
        );
    }

    @Override
    public int deleteById(final long id) {
        final String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
