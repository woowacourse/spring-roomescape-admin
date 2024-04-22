package roomescape.repository;

import java.util.HashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
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
}
