package roomescape.repository;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;
import roomescape.dto.TimeRequest;

@Repository
public class JdbcTimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.query(sql, rowMapper());
    }

    @Override
    public ReservationTime findById(long id) {
        String sql = "SELECT id, start_at FROM reservation_time where id = ?";
        return jdbcTemplate.queryForObject(sql, rowMapper(), id);
    }

    @Override
    public long save(TimeRequest timeRequest) {
        SimpleJdbcInsert insert = createInsert();
        Map<String, Object> params = createParams(timeRequest);
        return insert.executeAndReturnKey(params).longValue();
    }

    @Override
    public void deleteById(long id) {
        String sql = "DELETE FROM reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    private RowMapper<ReservationTime> rowMapper() {
        return (rs, rowNum) -> new ReservationTime(
                rs.getLong("id"),
                rs.getString("start_at")
        );
    }

    private SimpleJdbcInsert createInsert() {
        return new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    private Map<String, Object> createParams(TimeRequest request) {
        return Map.of("start_at", request.startAt());
    }
}
