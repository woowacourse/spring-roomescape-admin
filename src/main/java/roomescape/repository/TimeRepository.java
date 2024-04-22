package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.dto.TimeDto;
import roomescape.entity.Time;

import java.sql.PreparedStatement;
import java.util.List;

@Repository
public class TimeRepository {
    private final JdbcTemplate jdbcTemplate;

    public TimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Time> rowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            resultSet.getString("start_at")
    );

    public long insert(TimeDto timeDto) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES(?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, timeDto.startAt());
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }

    public List<Time> readAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper);
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
