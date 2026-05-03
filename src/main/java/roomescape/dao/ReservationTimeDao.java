package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<Map<String, Object>> findAll() {
        return jdbcTemplate.queryForList("SELECT id, start_at FROM reservation_time ORDER BY id");
    }

    public Map<String, Object> findById(long id) {
        return jdbcTemplate.queryForMap("SELECT id, start_at FROM reservation_time WHERE id = ?", id);
    }

    public long save(String startAt) {
        return simpleJdbcInsert.executeAndReturnKey(Map.of(
                "start_at", startAt
        )).longValue();
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
    }
}
