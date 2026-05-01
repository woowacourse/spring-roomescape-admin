package roomescape.dao;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private final RowMapper<ReservationTime> rowMapper = (rs, rowNum) ->
            new ReservationTime(
                    rs.getLong("id"),
                    rs.getObject("start_at", LocalTime.class)
            );

    public ReservationTimeDao(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT id, start_at FROM reservation_time";
        return jdbcTemplate.getJdbcTemplate().query(sql, rowMapper);
    }

    public ReservationTime findById(Long id) {
        String sql = "SELECT id, start_at FROM reservation_time WHERE id = :id";
        return jdbcTemplate.queryForObject(sql, new MapSqlParameterSource("id", id), rowMapper);
    }

    public Long save(LocalTime startAt) {
        return simpleJdbcInsert.executeAndReturnKey(
                new MapSqlParameterSource("start_at", startAt)
        ).longValue();
    }

    public void deleteById(Long id) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        jdbcTemplate.update(sql, new MapSqlParameterSource("id", id));
    }
}
