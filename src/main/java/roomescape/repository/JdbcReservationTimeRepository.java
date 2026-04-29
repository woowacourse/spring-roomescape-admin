package roomescape.repository;

import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import roomescape.domain.ReservationTime;

@RequiredArgsConstructor
@Repository
public class JdbcReservationTimeRepository implements ReservationTimeRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public ReservationTime findById(Long id) {
        return jdbcTemplate.queryForObject(
                "SELECT id, start_at FROM reservation_time WHERE id = ?",
                (rs, rowNum) -> ReservationTime.builder()
                        .id(rs.getLong("id"))
                        .startAt(LocalTime.parse(rs.getString("start_at")))
                        .build(),
                id
        );
    }
}
