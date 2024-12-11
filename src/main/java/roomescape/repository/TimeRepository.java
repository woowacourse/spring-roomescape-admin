package roomescape.repository;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

@Repository
public class TimeRepository {
    private static final RowMapper<Time> TIME_ROW_MAPPER = (rs, rowNum) -> {
        return new Time(
                rs.getLong("id"),
                rs.getTime("start_at").toLocalTime()
        );
    };

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .usingGeneratedKeyColumns("id")
                .withTableName("reservation_time");
    }

    public Time save(Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        Number key = jdbcInsert.executeAndReturnKey(params);
        return new Time(key.longValue(), time);
    }

    public Optional<Time> findById(Long id) {
        String sql = "select * from reservation_time where id = ?";
        try {
            Time time = jdbcTemplate.queryForObject(sql, TIME_ROW_MAPPER, id);
            return Optional.of(time);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Time> findAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, TIME_ROW_MAPPER);
    }

    public void delete(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
