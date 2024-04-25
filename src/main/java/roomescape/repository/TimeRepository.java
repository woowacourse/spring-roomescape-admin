package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.Time;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public TimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public Time insert(Time time) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(time);
        long id = jdbcInsert.executeAndReturnKey(params).intValue();

        return new Time(id, time.startAt());
    }

    public List<Time> list() {
        String sql = "SELECT * FROM reservation_time";
        List<Time> times = jdbcTemplate.query(sql, (resultSet, rowNum) -> {
            Time time = new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );
            return time;
        });

        return times;
    }

    public void delete(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
