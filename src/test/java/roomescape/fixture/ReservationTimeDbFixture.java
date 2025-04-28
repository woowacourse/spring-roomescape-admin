package roomescape.fixture;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import roomescape.domain.ReservationTime;

public class ReservationTimeDbFixture {
    private final SimpleJdbcInsert timeInsert;

    public ReservationTimeDbFixture(final JdbcTemplate jdbcTemplate) {
        this.timeInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("reservation_time")
                .usingGeneratedKeyColumns("id");
    }

    public ReservationTime 예약_시간_생성_10시() {
        LocalTime startAt = LocalTime.of(10, 0);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("start_at", startAt.toString());

        Number newId = timeInsert.executeAndReturnKey(parameters);

        return new ReservationTime(newId.longValue(), startAt);
    }
}
