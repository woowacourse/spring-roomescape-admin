package roomescape.reservation.repository;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import roomescape.reservation.entity.ReservationTime;

@Repository
public class ReservationTimeInMemoryRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ReservationTimeInMemoryRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public ReservationTime save(ReservationTime reservationTime) {
        String sql = "INSERT INTO reservation_time (start_at) VALUES (:startAt)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(sql, new MapSqlParameterSource("startAt", reservationTime.getStartAt()),keyHolder);

        Number key = keyHolder.getKey();
        return new ReservationTime(key.longValue(), reservationTime.getStartAt());
    }
}
