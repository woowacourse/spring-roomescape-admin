package roomescape.time.infrastructure;

import java.time.LocalTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.controller.TimeRepository;
import roomescape.time.domain.Time;

@Repository
public class TimeRepositoryImpl implements TimeRepository {

    private final JdbcTemplate jdbcTemplate;

    public TimeRepositoryImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Time save(Time time) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");

        SqlParameterSource parameters = new MapSqlParameterSource()
                .addValue("start_at", time.getStartAt());
        Long id = jdbcInsert.executeAndReturnKey(parameters).longValue();

        return new Time(id, time.getStartAt());
    }

    @Override
    public List<Time> findAll() {
        String sql = "select * from reservation_time";
        return jdbcTemplate.query(sql, (resultSet, rowNum) ->
                new Time(
                        resultSet.getLong("id"),
                        LocalTime.parse(resultSet.getString("start_at"))
                ));
    }

    @Override
    public void deleteById(Long id) {
        String sql = "delete from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }
}
