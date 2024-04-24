package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDaoImpl implements TimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("start_at"))
    );

    public TimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }
}
