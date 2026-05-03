package roomescape.dao;

import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Map<String, Object>> findAll() {
        return jdbcTemplate.queryForList("""
                SELECT
                    r.id AS reservation_id,
                    r.name,
                    r.date,
                    t.id AS time_id,
                    t.start_at
                FROM reservation AS r
                INNER JOIN reservation_time AS t
                    ON r.time_id = t.id
                ORDER BY r.id
                """);
    }

    public long save(String name, String date, long timeId) {
        return simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", name,
                "date", date,
                "time_id", timeId
        )).longValue();
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
