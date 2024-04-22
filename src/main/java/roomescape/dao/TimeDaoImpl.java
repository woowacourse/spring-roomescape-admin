package roomescape.dao;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.domain.Time;

@Component
public class TimeDaoImpl implements TimeDao {
    private final JdbcTemplate jdbcTemplate;

    public TimeDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Time> findAll() {
        return jdbcTemplate.query("SELECT * FROM reservation_time", (rs, rowNum) -> new Time(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        ));
    }

    @Override
    public void save(Time time) {
        if (existsById(time.getId())) {
            throw new IllegalArgumentException("duplicate id exists");
        }
        jdbcTemplate.update("INSERT INTO reservation_time (id, start_at) VALUES (?, ?)"
                , time.getId(), java.sql.Time.valueOf(time.getStartAt()));
    }

    @Override
    public boolean deleteById(long id) {
        boolean exists = existsById(id);
        jdbcTemplate.update("DELETE FROM reservation_time WHERE id = ?", id);
        return exists;
    }

    private boolean existsById(long id) {
        long count = jdbcTemplate.queryForObject(
                "SELECT COUNT(1) FROM reservation_time WHERE id = ?", Long.class, id);
        return count > 0;
    }
}
