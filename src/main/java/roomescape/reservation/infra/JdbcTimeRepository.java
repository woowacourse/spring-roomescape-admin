package roomescape.reservation.infra;

import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.domain.Time;

@Repository
@RequiredArgsConstructor
public class JdbcTimeRepository implements TimeRepository {
    private final NamedParameterJdbcTemplate template;

    @Override
    public Time save(LocalTime startAt) {
        String sql = "INSERT INTO reservation_time(start_at) VALUES (:start_at)";

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("start_at", startAt.toString());

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, params, keyHolder);

        return new Time(keyHolder.getKey().longValue(), startAt);
    }
}
