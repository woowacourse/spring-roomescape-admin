package roomescape.time.dao;

import java.sql.Time;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import roomescape.common.Dao;

@Component
public class H2TimeDao implements Dao<Time> {
    private final JdbcTemplate jdbcTemplate;

    public H2TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Time add(Time time) {
        return null;
    }

    @Override
    public List<Time> getAll() {
        return List.of();
    }

    @Override
    public void deleteById(Long id) {

    }
}
