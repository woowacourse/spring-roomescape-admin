package roomescape.time.dao;

import java.sql.Time;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.time.domain.ReservationTime;
import roomescape.time.dto.TimeRequest;

@Repository
public class TimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    public TimeDAO(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAllTimes() {
        String sql = "SELECT * from reservation_time";
        List<ReservationTime> reservationTimes = jdbcTemplate.query(
                sql,
                (resultSet, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultSet.getLong("id"),
                            resultSet.getTime("start_at").toLocalTime()
                    );
                    return reservationTime;
                });
        return reservationTimes;
    }

    public ReservationTime insertTime(final TimeRequest timeRequest) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", Time.valueOf(timeRequest.startAt()));

        Number insertedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new ReservationTime(insertedId.longValue(), timeRequest.startAt());
    }

    public void deleteTime(final Long id) {
        String sql = "DELETE from reservation_time where id = ?";
        jdbcTemplate.update(sql, id);
    }

    public ReservationTime findReservationTimeById(final Long id) {
        String sql = "SELECT id, start_at from reservation_time where id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (resultset, rowNum) -> {
                    ReservationTime reservationTime = new ReservationTime(
                            resultset.getLong("id"),
                            resultset.getTime("start_at").toLocalTime()
                    );
                    return reservationTime;
                }, id);
    }
}
