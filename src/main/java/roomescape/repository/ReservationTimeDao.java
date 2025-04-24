package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.service.domain.ReservationTime;

@Repository
public class ReservationTimeDao {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert reservationTimeInserter;
    private final RowMapper<ReservationTime> reservationTimeRowMapper = (resultSet, rowNum) ->
            new ReservationTime(
                    resultSet.getLong("id"),
                    LocalTime.parse(resultSet.getString("start_at"))
            );

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate, final DataSource dataSource) {
        this.jdbcTemplate = jdbcTemplate;
        this.reservationTimeInserter = new SimpleJdbcInsert(dataSource)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime createReservationTime(final ReservationTime reservationTime) {
        final Long id = insertReservationTimeAndRetrieveKey(reservationTime);
        return getReservationTimeById(id);
    }

    public List<ReservationTime> getReservationTimes() {
        final String sql = "SELECT id, start_at from reservation_time";
        return jdbcTemplate.query(sql, reservationTimeRowMapper);
    }

    public void deleteReservationTimeById(final long id) {
        final String sql = "DELETE FROM reservation_time WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private ReservationTime getReservationTimeById(final long id) {
        final String sql = "SELECT id, start_at FROM reservation_time WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, reservationTimeRowMapper, id);
    }

    private long insertReservationTimeAndRetrieveKey(final ReservationTime reservationTime) {
        final Map<String, Object> parameters = new HashMap<>(Map.of("start_at", reservationTime.getStartAt()));
        return (long) reservationTimeInserter.executeAndReturnKey(parameters);
    }
}
