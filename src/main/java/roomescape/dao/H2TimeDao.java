package roomescape.dao;

import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import roomescape.controller.dto.ReservationTimeAddRequest;
import roomescape.domain.ReservationTime;

@Repository
public class H2TimeDao implements TimeDao {
    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    public H2TimeDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id")
                .usingColumns("start_at");
    }

    @Override
    public ReservationTime add(ReservationTimeAddRequest request) {
        Map<String, Object> parameters = Map.of(
                "start_at", request.startTime()
        );
        Number key = simpleJdbcInsert.executeAndReturnKey(parameters);
        return new ReservationTime(key.longValue(), request.startTime());
    }
}
