package roomescape.repository;

import java.time.LocalTime;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate, DataSource source) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(source)
            .withTableName("reservation_time")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime save(ReservationTime time) {
        SqlParameterSource params = new MapSqlParameterSource()
            .addValue("start_at", time.getStartAt());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return findById(id);
    }

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
            "select id, start_at from reservation_time where id=?",
            (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
            ), id
        );
    }

    @Override
    public List<ReservationTime> findAll() {
        return jdbcTemplate.query(
            "select id, start_at from reservation_time",
            (resultSet, rowNum) -> new ReservationTime(
                resultSet.getLong("id"),
                LocalTime.parse(resultSet.getString("start_at"))
            )
        );
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update("delete from reservation_time where id = ?", id);
    }
}
