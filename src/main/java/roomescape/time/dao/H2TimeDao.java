package roomescape.time.dao;

import java.sql.PreparedStatement;
import java.util.List;
import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import roomescape.common.Dao;
import roomescape.time.Time;

@Component
public class H2TimeDao implements Dao<Time> {
    private final JdbcTemplate jdbcTemplate;

    public H2TimeDao(JdbcTemplate jdbcTemplate, ConnectionDetails connectionDetails) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Time add(Time time) {
        String sql = "insert into reservation_time(start_at) values (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, new String[]{"id"});
            preparedStatement.setString(1, time.getStartAt().toString());
            return preparedStatement;
        }, keyHolder);

        Long id = keyHolder.getKey().longValue();
        return new Time(id, time.getStartAt());
    }

    @Override
    public List<Time> getAll() {
        String sql = "select id, start_at from reservation_time";
        return jdbcTemplate.query(sql,
                (resultSet, rowNum) -> new Time(
                        resultSet.getLong("id"),
                        resultSet.getTime("start_at").toLocalTime()
                ));
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
