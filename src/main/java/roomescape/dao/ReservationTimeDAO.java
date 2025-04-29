package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

@Repository
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) -> new ReservationTime(
            resultSet.getLong("id"),
            resultSet.getTime("start_at").toLocalTime()
    );

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingColumns("start_at")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", reservationTimeRowMapper);
    }

    public ReservationTime addAndGet(LocalTime startAt) {
        Map<String, Object> parameters = Map.of("start_at", startAt);

        Number id = simpleJdbcInsert.executeAndReturnKey(parameters);

        return new ReservationTime(id.longValue(), startAt);
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
