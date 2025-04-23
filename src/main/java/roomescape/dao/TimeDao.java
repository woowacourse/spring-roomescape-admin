package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
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

    public Long saveTime(Time time) {
        String sql = "INSERT INTO time (time) values(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setTime(3, java.sql.Time.valueOf(time.getTime()));
            return ps;
        }, keyHolder);
        return keyHolder.getKey().longValue();
    }
}
