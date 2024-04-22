package roomescape.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.Time;

@Repository
public class JdbcTemplateTimeRepository implements TimeRepository {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcTemplateTimeRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public Time save(Time timeRequest) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", timeRequest.getStartAt());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return new Time(id, timeRequest.getStartAt());
    }

    @Override
    public List<Time> findAll() {
        RowMapper<Time> timeRowMapper = (resultSet, rowNum) -> new Time(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
        return jdbcTemplate.query("SELECT id, start_at FROM reservation_time", timeRowMapper);
    }

    @Override
    public void deleteById(Long id) {
        int rowCount = jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        if (rowCount == 0) {
            throw new IllegalArgumentException("존재하지 않는 시간입니다.");
        }
    }
}
