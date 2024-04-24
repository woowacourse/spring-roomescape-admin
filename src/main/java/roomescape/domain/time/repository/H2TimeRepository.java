package roomescape.domain.time.repository;

import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.time.Time;

@Repository
public class H2TimeRepository implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public H2TimeRepository(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public List<Time> findAllTimes() {
        String sql = "SELECT * FROM reservation_time";
        RowMapper<Time> rowMapper = (resultSet, rowNum) -> {
            Time time = new Time(
                    resultSet.getLong("id"),
                    resultSet.getTime("start_at").toLocalTime()
            );
            return time;
        };

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Time createTime(Time requestTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", requestTime.getStartAt());
        Long id = jdbcInsert.executeAndReturnKey(params).longValue();

        return new Time(id, requestTime.getStartAt());
    }

    @Override
    public void deleteTime(Long id) {

    }
}
