package roomescape.reservation.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Component;
import roomescape.reservation.model.ReservationTime;

import java.util.HashMap;
import java.util.Map;

@Component
public class ReservationTimeDAO {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime insert(ReservationTime reservationTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTime.getStartAt());
        Long timeId = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return new ReservationTime(timeId, reservationTime.getStartAt());
    }
}
