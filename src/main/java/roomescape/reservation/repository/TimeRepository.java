package roomescape.reservation.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.reservation.model.Time;
import roomescape.reservation.model.TimeDetails;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public TimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public Time insertTime(TimeDetails timeDetails) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", timeDetails.startAt());
        Number number = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new Time(number.longValue(), timeDetails.startAt());
    }

    public List<Time> findAll() {
        String sql = "Select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getObject("start_at", LocalTime.class)
        ));
    }

    public boolean deleteTimeById(long id) {
        String sql = "delete from reservation_time where id = ?";
        int updated = jdbcTemplate.update(sql, id);
        return updated != 0;
    }
}
