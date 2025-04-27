package roomescape.reservation.dao;

import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.ReservationTime;

@Repository
public class JdbcReservationTimeDao implements ReservationTimeDao {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final RowMapper<ReservationTime> reservationRowMapper = (resultSet, rowNum) -> {
        return new ReservationTime(
                resultSet.getLong("id"),
                resultSet.getTime("start_at").toLocalTime()
        );
    };

    public JdbcReservationTimeDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbcTemplate = namedParameterJdbcTemplate;
    }

    public List<ReservationTime> findAll() {
        String sql = "SELECT * FROM reservation_time";
        return jdbcTemplate.query(sql, reservationRowMapper);
    }

    public ReservationTime save(ReservationTime reservationTime) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO reservation_time(start_at) VALUES(:start_at)";
        MapSqlParameterSource parameters = new MapSqlParameterSource("start_at", reservationTime.getStartAt());
        jdbcTemplate.update(sql, parameters, keyHolder, new String[]{"id"});
        return new ReservationTime(keyHolder.getKeyAs(Long.class), reservationTime.getStartAt());
    }

    public boolean removeById(long id) {
        String sql = "DELETE FROM reservation_time WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        int rowNumber = jdbcTemplate.update(sql, parameters);
        return rowNumber == 1;
    }

    public ReservationTime getById(long id) {
        String sql = "SELECT * FROM reservation_time WHERE id = :id";
        MapSqlParameterSource parameters = new MapSqlParameterSource("id", id);
        return jdbcTemplate.queryForObject(sql, parameters, reservationRowMapper);
    }
}
