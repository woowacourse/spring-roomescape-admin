package roomescape.repository;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@Repository
public class ReservationTimeRepositoryImpl implements ReservationTimeRepository {

    private final SimpleJdbcInsert simpleJdbcInsert;

    public ReservationTimeRepositoryImpl(final JdbcTemplate jdbcTemplate) {
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    @Override
    public ReservationTime insert(final LocalTime startAt) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", startAt);
        long id = (long) simpleJdbcInsert.executeAndReturnKey(parameters);
        return new ReservationTime(id, startAt);
    }
}
