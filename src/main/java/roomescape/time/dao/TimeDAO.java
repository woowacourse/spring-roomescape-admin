package roomescape.time.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;
import roomescape.time.dto.TimeRequest;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    public TimeDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Time> findAllTimes() {
        String sql = "SELECT * from reservation_time";
        List<Time> times = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    Time time = new Time(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );
                    return time;
                });
        return times;
    }

    public Time insertTime(TimeRequest timeRequest) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", timeRequest.startAt());

        Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Time(insertedId.longValue(), timeRequest.startAt());
    }

    public void deleteTime(Long id) {
        String sql = "DELETE from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
