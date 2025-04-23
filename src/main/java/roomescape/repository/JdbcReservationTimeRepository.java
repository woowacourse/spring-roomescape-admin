package roomescape.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.model.ReservationTime;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public JdbcReservationTimeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public boolean existByStartAt(LocalTime startAt) {
        String sql = "SELECT COUNT(*) FROM reservation_time WHERE startAt = ?";
        return jdbcTemplate.queryForObject(sql, Long.class, startAt) > 0L;
    }

    @Override
    public ReservationTime insertAndGet(ReservationTime reservationTime) {
        Map<String, Object> params = new HashMap<>();
        params.put("start_at", reservationTime.getStartAt());
        Long id = simpleJdbcInsert.executeAndReturnKey(params).longValue();
        return ReservationTime.toEntity(reservationTime, id);
    }
}
