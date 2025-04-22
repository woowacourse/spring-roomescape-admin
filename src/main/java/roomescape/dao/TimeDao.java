package roomescape.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Component;
import roomescape.entity.TimeEntity;

import java.sql.PreparedStatement;
import java.time.LocalTime;
import java.util.List;

@Component
public class TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public TimeEntity save(TimeEntity entity) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, entity.startAt().toString());
            return preparedStatement;
        }, keyHolder);
        final long id = keyHolder.getKey().longValue();
        return new TimeEntity(id, entity.startAt());
    }

    public List<TimeEntity> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Long id = resultSet.getLong("id");
            LocalTime time = resultSet.getObject("start_at", LocalTime.class);
            return new TimeEntity(id, time);
        });
    }

    public void deleteById(final Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        final int deleted = jdbcTemplate.update(sql, id);
        if (deleted == 0) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
