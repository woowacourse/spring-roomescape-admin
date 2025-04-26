package roomescape.dao;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeDao {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert insertReservationTime;

    public ReservationTimeDao(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.insertReservationTime = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public List<ReservationTime> findAll() {
        String sql = "select id, start_at from reservation_time";
        List<ReservationTime> foundReservationTimes = jdbcTemplate.query(
                sql, (rs, rowNum) -> {
                    long id = rs.getLong("id");
                    String startAt = rs.getString("start_at");
                    return new ReservationTime(id, LocalTime.parse(startAt));
                }
        );
        return foundReservationTimes;
    }

    public ReservationTime findById(long id) {
        String sql = "select start_at from reservation_time where id = ?";
        LocalTime time = jdbcTemplate.queryForObject(sql, LocalTime.class, id);

        return new ReservationTime(id, time);

    }

    public ReservationTime insert(ReservationTime reservationTime) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", reservationTime.getStartAt());
        long newId = insertReservationTime.executeAndReturnKey(parameters).longValue();

        return new ReservationTime(newId, reservationTime);
    }

    public int deleteById(long id) {
        String sql = "delete from reservation_time where id = ?";
        return jdbcTemplate.update(sql, id);
    }
}
