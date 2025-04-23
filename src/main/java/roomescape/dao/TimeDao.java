package roomescape.dao;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import roomescape.model.Time;

@Repository
public class TimeDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Time> actorRowMapper = (resultSet, rowNum) -> {
        Time time = new Time(
                resultSet.getLong("id"),
                resultSet.getTime("time").toLocalTime()
        );
        return time;
    };

    public List<Time> findAll() {
        String sql = "SELECT * FROM time";
        return jdbcTemplate.query(sql, actorRowMapper);
    }

}
