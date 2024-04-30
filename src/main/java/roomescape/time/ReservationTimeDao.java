package roomescape.time;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert jdbcInsert;

    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, __) -> new ReservationTime(
        resultSet.getLong("id"),
        resultSet.getTime("start_at").toLocalTime()
    );

    public ReservationTimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        return jdbcTemplate.query("SELECT * FROM RESERVATION_TIME", reservationTimeRowMapper);
    }

    public ReservationTime findById(long id) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM RESERVATION_TIME WHERE ID = ?", reservationTimeRowMapper, id
        );
    }

    public ReservationTime save(ReservationTime reservationTime) {
        SqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", reservationTime.startAt());
        long id = jdbcInsert.executeAndReturnKey(params).longValue();
        return findById(id);
    }

    public void delete(long id) {
        jdbcTemplate.update("DELETE FROM RESERVATION_TIME WHERE ID = ?", id);
    }
}
