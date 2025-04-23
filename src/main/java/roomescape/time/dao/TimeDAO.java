package roomescape.time.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.Time;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    public TimeDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
}
