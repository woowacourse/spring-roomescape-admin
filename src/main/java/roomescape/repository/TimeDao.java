package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("start_at"))
    );

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public long save(Time time) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(
                    "INSERT INTO time (start_at) VALUES (?)",
                    new String[]{"id"});
            ps.setString(1, time.getStartAt().toString());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Time> findAll() {
        List<Time> times = jdbcTemplate.query("SELECT * FROM time", timeRowMapper);
        return new ArrayList<>(times);
    }

    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM time WHERE id = ?", id);
    }
}
