package roomescape.domain.time.repository;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.Time;

@Repository
public class H2TimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public H2TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Time> findAllTimes() {
        String sql = "SELECT * FROM reservation_time";
        RowMapper<Time> rowMapper = (resultSet, rowNum) -> {
            Time time = new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("startAt").toLocalTime()
            );
            return time;
        };
        
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Time createTime(Time time) {
        return null;
    }

    @Override
    public void deleteTime(Long id) {

    }
}
