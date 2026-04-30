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

    public ReservationDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation")
                .usingGeneratedKeyColumns("id");
    }

    public List<Map<String, Object>> findAll() {
        return jdbcTemplate.queryForList("SELECT id, name, date, time FROM reservation ORDER BY id");
    }

    public long save(final String name, final String date, final String time) {
        return simpleJdbcInsert.executeAndReturnKey(Map.of(
                "name", name,
                "date", date,
                "time", time
        )).longValue();
    }

    public void delete(final long id) {
        jdbcTemplate.update("DELETE FROM reservation WHERE id = ?", id);
    }
}
