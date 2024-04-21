package roomescape.dao;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Objects;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.dto.TimeDto;

@Component
public class TimeDao {

    private final JdbcTemplate jdbcTemplate;

    public TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<TimeDto> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) -> new TimeDto(
                resultSet.getLong("id"),
                resultSet.getString("start_at")
        ));
    }

    public TimeDto findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultSet, rowNum) -> new TimeDto(
                        resultSet.getLong("id"),
                        resultSet.getString("start_at")
                ),
                id);
    }

    public long add(TimeDto timeDto) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(
                connection -> {
                    PreparedStatement preparedStatement
                            = connection.prepareStatement(sql, new String[]{"id"});
                    preparedStatement.setString(1, timeDto.getStartAt());
                    return preparedStatement;
                },
                keyHolder
        );
        return Objects.requireNonNull(keyHolder.getKey()).longValue();
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
