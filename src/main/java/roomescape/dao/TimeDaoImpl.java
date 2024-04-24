package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeDaoImpl implements TimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;
    private final RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> new Time(
            resultSet.getLong("id"),
            LocalTime.parse(resultSet.getString("start_at"))
    );

    public TimeDaoImpl(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Time> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, timeRowMapper);
    }

    @Override
    public Optional<Time> findById(Long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = ?";
        Time time = jdbcTemplate.queryForObject(sql, timeRowMapper, id);
        return Optional.ofNullable(time);
    }

    @Override
    public void save(Time time) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", time.getStartAt());
        Long id = (Long) jdbcInsert.executeAndReturnKey(parameters);
        time.setId(id);
    }

    @Override
    public void delete(Time time) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, time.getId());
    }
}
